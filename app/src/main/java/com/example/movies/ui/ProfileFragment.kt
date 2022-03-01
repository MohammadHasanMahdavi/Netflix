package com.example.movies.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.example.movies.R
import com.example.movies.data.NetWork.*
import com.example.movies.databinding.FragmentProfileBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentProfileBinding? = null
    private val binding  get() = _binding!!
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2
    lateinit var bitmap: Bitmap
    lateinit var path : Uri


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getImage()
        sharedPreferences = this.requireActivity().getSharedPreferences("User-Information", Context.MODE_PRIVATE)

        val name = sharedPreferences.getString("NAME","")
        binding.fullNameText.setText(name)

        val userName = sharedPreferences.getString("USERNAME","")
        binding.usernameText.setText(userName)

        val email = sharedPreferences.getString("EMAIL","")
        binding.emailText.setText(email)

        val phone = sharedPreferences.getString("PHONE-NUMBER","")
        binding.phoneText.setText(phone)

        val password = sharedPreferences.getString("PASSWORD","")
        binding.passwordText.setText(password)
        binding.retypePasswordText.setText(password)

        binding.registerButton.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            val name2 = binding.fullNameText.text.toString()
            val username = binding.usernameText.text.toString()
            val email2 = binding.emailText.text.toString()
            val phoneNumber = binding.phoneText.text.toString()
            val password2 = binding.passwordText.text.toString()
            //val reTypePassword = binding.retypePasswordText.text.toString()

            editor.putString("NAME",name2)
            editor.putString("USERNAME",username)
            editor.putString("EMAIL",email2)
            editor.putString("PHONE-NUMBER",phoneNumber)
            editor.putString("PASSWORD",password2)

            editor.apply()
            Toast.makeText(context, "Information Saved", Toast.LENGTH_SHORT).show()
        }

        binding.cardView.setOnClickListener {
            AlertDialog.Builder(context)
                .setMessage("choose")
                .setNegativeButton("GALLERY") { _, _ ->
                    galleryCheckPermission()
                }
                .setPositiveButton("CAMERA"){_,_ ->
                    cameraCheckPermission()
                }.show()
        }
    }

    private fun uploadImage(){
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,75,bos)
        val imageInBytes = bos.toByteArray()

        val requestBody = imageInBytes.toRequestBody("image/*".toMediaTypeOrNull(),0,imageInBytes.size)
        val part : MultipartBody.Part = MultipartBody.Part.createFormData("MHM81","MHM81",requestBody)

        val client = NetworkManager.service
        val call  = client?.uploadImage("MHM81.jpg",part)
        call?.enqueue(object : Callback<RequestBody> {
            override fun onResponse(call: Call<RequestBody>, response: Response<RequestBody>) {
                Toast.makeText(context, "onResponseUpload", Toast.LENGTH_SHORT).show()
                Log.d("onResponseUpload" , response.toString())            }

            override fun onFailure(call: Call<RequestBody>, t: Throwable) {
                t.message?.let { Log.d("TAG", it) }
                Toast.makeText(context, "upload failed", Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun getImage(){
        val client = NetworkManager.service
        val call = client?.getImage("MHM81.jpg")
        call!!.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(context, "onResponseDownload", Toast.LENGTH_SHORT).show()
                Log.d("onResponseDownload" , response.toString())

                if(response.body() != null) {
                    val stream = response.body()!!.byteStream()
                    stream.let {
                        val bitmap: Bitmap =
                            BitmapFactory.decodeStream(stream)
                        binding.profileImageView.setImageBitmap(bitmap)
                    }
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "download failed", Toast.LENGTH_SHORT).show()
            }

        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        path  = data?.data!!
        bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver,path)
        if(resultCode == Activity.RESULT_OK)
            when(requestCode)
            {
                CAMERA_REQUEST_CODE -> {
                    val cameraBitmap = data.extras?.get("data") as Bitmap
                    binding.profileImageView.load(cameraBitmap){
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())

                    }
                    uploadImage()
                }
                GALLERY_REQUEST_CODE -> {
                    binding.profileImageView.load(data!!.data) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())
                        uploadImage()
                    }
                }
            }
    }
    private fun galleryCheckPermission() {
        Dexter.withContext(context)
            .withPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ).withListener(object : PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    gallery()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(context, "You have denied the storage permission", Toast.LENGTH_SHORT).show()
                    showRotationalDialogForPermission()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    showRotationalDialogForPermission()
                }
            }).onSameThread().check()
    }
    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,GALLERY_REQUEST_CODE)
    }
    private fun cameraCheckPermission() {
        Dexter.withContext(context)
            .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA).withListener(
                object : MultiplePermissionsListener{
                    override fun onPermissionsChecked(report : MultiplePermissionsReport?) {
                        report?.let {
                            if (report.areAllPermissionsGranted())
                                camera()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        showRotationalDialogForPermission()
                    }

                }
            ).onSameThread().check()
    }
    private fun camera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,CAMERA_REQUEST_CODE)
    }
    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(context)
            .setMessage("It looks like you have turned off permissions"
            +"required for this feature. It can be enable under app setting!!")
            .setPositiveButton("Go To Setting"){_,_ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package",activity?.packageName,null)
                    intent.data = uri
                    startActivity(intent)
                }catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CANCEL"){dialog,_ ->
                dialog.dismiss()
            }.show()
    }

}