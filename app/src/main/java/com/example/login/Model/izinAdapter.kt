import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.login.Model.Model_izin
import com.example.login.R

class izinAdapter(
    private val context: Context,
    private val izinList: MutableList<Model_izin>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<izinAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
        fun onUpdatedItem(updatedItem: Model_izin)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.tv_nama)
        private val textViewTime: TextView = itemView.findViewById(R.id.tv_waktu_absen)
        private val textViewKet: TextView = itemView.findViewById(R.id.tv_keterangan)
        private val ivEdit: ImageView = itemView.findViewById(R.id.editButton)
        private val ivDelete: ImageView = itemView.findViewById(R.id.iv_delete)

        init {
            ivEdit.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onEditClick(position)
                }
            }

            ivDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onDeleteClick(position)
                    deleteItem(position)
                }
            }
        }

        fun bind(izin: Model_izin) {
            textViewName.text = izin.nama
            textViewTime.text = izin.waktuAbsen
            textViewKet.text = izin.keterangan
        }

        private fun deleteItem(position: Int) {
            if (deleteItemFromDatabase(position)) {
                izinList.removeAt(position)
                notifyItemRemoved(position)
            }
        }

        private fun deleteItemFromDatabase(position: Int): Boolean {
            return if (position in izinList.indices) {
                val deletedItem = izinList[position]
                val dbHelper = DatabaseHelper(context)
                deletedItem.id?.let {
                    dbHelper.deleteIzin(deletedItem)
                } ?: false
            } else {
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_izin_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val izin = izinList[position]
        holder.bind(izin)
    }

    override fun getItemCount(): Int {
        return izinList.size
    }

}
