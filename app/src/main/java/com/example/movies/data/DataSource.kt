package com.example.movies.data

import com.example.movies.R
import com.example.movies.data.models.Movie

class DataSource {

        fun createDataSet() : ArrayList<Movie>
        {
            val list = ArrayList<Movie>()
            list.add(Movie("Censor 2021", R.drawable.censor_2021,false))

            list.add(
                Movie("Nope 2022", R.drawable.nope_2022,false)
            )

            list.add(
                Movie("Slapface 2021", R.drawable.slapface_2021,false)
            )

            list.add(
                Movie("Spiral 2021", R.drawable.spiral_2021,false)
            )

            list.add(
                Movie("The Forever Purge 2021", R.drawable.the_forever_purge_2021,false)
            )

            list.add(
                Movie("The Unholy 2021", R.drawable.the_unholy_2021,false)
            )

            list.add(
                Movie("Black Box 2020", R.drawable.black_box_2020,false)
            )

            list.add(
                Movie("Immanence 2022", R.drawable.immanence_2022,false)
            )

            list.add(
                Movie("Lamb 2021" , R.drawable.lamb_2021,false)
            )

            list.add(
                Movie("Scream 2022", R.drawable.scream_2022,false)
            )

            list.add(
                Movie("Shut In 2022", R.drawable.shut_in_2022,false)
            )

            list.add(
                Movie("Guilty 2021", R.drawable.the_guilty_2021,false)
            )

            list.add(
                Movie("The Privilege 2022" , R.drawable.the_privilage_2022, false)
            )

            list.add(
                Movie("Agens 2021", R.drawable.agnes_2021,false)
            )

            list.add(
                Movie("Antlers 2021", R.drawable.antlers_2021,false)
            )

            list.add(
                Movie("Evil Eye 2020", R.drawable.evil_eye_2020,false)
            )

            list.add(
                Movie("Hall 2020" , R.drawable.hall_2020,false)
            )

            list.add(
                Movie("Hypnotic 2020", R.drawable.hypnotic_2021,false)
            )

            list.add(
                Movie("Motherly 2021" , R.drawable.motherly_2021,false)
            )

            list.add(
                Movie("The Feast 2021" , R.drawable.the_feast_2021,false)
            )

            list.add(
                Movie("The Manor 2021" , R.drawable.the_manor_2021,false)
            )

            return list
        }

}