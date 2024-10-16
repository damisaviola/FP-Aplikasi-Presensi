import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.login.Model.Model_presensi
import com.example.login.R

class itemAdapter(
    private val presensiList: MutableList<Model_presensi>
) : RecyclerView.Adapter<itemAdapter.ViewHolder>() {

    private var itemClickListener: ((Model_presensi) -> Unit)? = null

    fun setOnItemClickListener(listener: (Model_presensi) -> Unit) {
        itemClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.tv_nama)
        private val textViewTime: TextView = itemView.findViewById(R.id.tv_waktu_absen)
        private val textViewKet: TextView = itemView.findViewById(R.id.tv_keterangan)
        private val textViewLok: TextView = itemView.findViewById(R.id.tv_lokasi)
        fun bind(presensi: Model_presensi) {
            textViewName.text = presensi.nama
            textViewTime.text = presensi.waktuAbsen
            textViewKet.text = presensi.keterangan
            textViewLok.text = presensi.lokasi

            itemView.setOnClickListener {
                itemClickListener?.invoke(presensi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.histroy_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val presensi = presensiList[position]
        holder.bind(presensi)
    }

    override fun getItemCount(): Int {
        return presensiList.size
    }

    fun getItemAtPosition(position: Int): Model_presensi {
        return presensiList[position]
    }


}
