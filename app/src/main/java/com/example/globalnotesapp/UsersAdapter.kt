package com.example.globalnotesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.globalnotesapp.models.User
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class UsersAdapter(options: FirestoreRecyclerOptions<User>, val listener: IUserAdapter): FirestoreRecyclerAdapter<User, UsersAdapter.UsersViewHolder>(
    options
) {

    class UsersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var userName: TextView = itemView.findViewById(R.id.tv_user_name)
        val userEmail: TextView = itemView.findViewById(R.id.tv_user_email)
        val userData: LinearLayout = itemView.findViewById(R.id.user_data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val userViewHolder = UsersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_users_item, parent, false))
        userViewHolder.userData.setOnClickListener{
            listener.onCardClicked(snapshots.getSnapshot(userViewHolder.adapterPosition).id)
        }
        return userViewHolder
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int, model: User) {
        val auth = Firebase.auth
        val currentUser: String = auth.currentUser!!.uid
        if(currentUser == model.uid){
            holder.itemView.visibility = View.GONE
        }
        holder.userName.text = model.name
        holder.userEmail.text = model.email
    }

}
interface IUserAdapter{
    fun onCardClicked(uid: String)
}