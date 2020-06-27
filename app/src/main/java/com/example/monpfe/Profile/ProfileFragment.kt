package com.example.monpfe.Profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.facebook.FacebookSdk.getApplicationContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

import com.example.monpfe.Change_Password
import com.example.monpfe.HomeActivity
import com.example.monpfe.Login.LoginActivity
import com.example.monpfe.R
import com.firebase.client.Firebase
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task


import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.reycler_deplacement.*
import java.io.ByteArrayOutputStream

import java.io.IOException
import java.util.*


class ProfileFragment(): Fragment() {
    lateinit var user :FirebaseUser

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var buttonTakePhoto: ImageButton

    lateinit var firebaseStorage: FirebaseStorage
    lateinit var firebaseDatabase: FirebaseDatabase
    var storageRefe : StorageReference? = null
    lateinit var imageView: ImageView
    lateinit var v: View
    lateinit var userId : String
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_profile, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth=FirebaseAuth.getInstance()
        user=firebaseAuth.currentUser!!

        if (user != null)
        {

        }
        ajouter_ph.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)

        }
        /*
        Glide.with(this).load(FirebaseStorage.getInstance().getReference().downloadUrl)
            //.placeholder(R.drawable.ic_launcher_background)
            //  .error(R.drawable.ic_launcher_background)
            .into(profile_pic_imageView)
*/
        var reference = FirebaseDatabase.getInstance().getReference("Utilisateurs")
        reference.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(p0: DataSnapshot) {
                textViewEmailAdress.setText(user!!.getEmail())
                val auth = FirebaseAuth.getInstance()
                val currentUser = auth.currentUser
                val id =currentUser!!.uid


                val value = p0.child("$id").getValue(utilisateur_data()::class.java)

                val name = value?.name
                val phone=value?.phone
                prenom.text = name
                numtel.text = phone


                Log.i("firebaseVal"," id = $id, name: $numtel  phone $prenom")

            }
            override  fun onCancelled(databaseError:DatabaseError) {
                Toast.makeText(context, databaseError.getCode(), Toast.LENGTH_SHORT).show()
            }
        })


        storageRefe = FirebaseStorage.getInstance().reference.child("User Image")
        userId = firebaseAuth.currentUser!!.uid
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        imageView = view.profile_pic_imageView

        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(Intent(getApplicationContext(), LoginActivity::class.java))
        }


        btn_log_out.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val inent = Intent(context as HomeActivity, LoginActivity::class.java)
            startActivity(inent)
        }
        btn_changer_mdp.setOnClickListener {
            var dialogue= Change_Password()
            dialogue.show((context as HomeActivity).supportFragmentManager,null)
        }
    }

    var selectedPhotoUri : Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        selectedPhotoUri = data!!.data

        try {
            selectedPhotoUri?.let {
                if(Build.VERSION.SDK_INT < 28) {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        selectedPhotoUri
                    )
                    imageView.setImageBitmap(bitmap)
                } else {
                    val source = ImageDecoder.createSource(requireContext().contentResolver, selectedPhotoUri!!)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    imageView.setImageBitmap(bitmap)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        uplodeToFirebaseStorage()
    }

    private fun uplodeToFirebaseStorage() {
        if (selectedPhotoUri == null) {
            return
        }else{
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

             fun download(){
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("profile","file location:  $it")
                }
                 .addOnFailureListener{
                    ref.downloadUrl.addOnSuccessListener {
                        Log.d("profile","downloaded in second try file location:  $it")
                    }
                    Log.d("profile","failed to download image file location:  $it")
                }//if here is problem you will get log
            }

            ref.putFile(selectedPhotoUri!!).addOnSuccessListener {
                Log.d("profile","image uploded ${it.metadata?.path}")
                download()
            }.addOnFailureListener{
                ref.putFile(selectedPhotoUri!!).addOnSuccessListener {
                    Log.d("profile","image uploded in second try ${it.metadata?.path}")
                    download()
                }// if something goes wrong it try once again
                Log.d("profile","image not uploded ${it}")
            }//if here is problem you will get log




        }

    }
}