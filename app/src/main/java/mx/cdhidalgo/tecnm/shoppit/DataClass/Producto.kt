package mx.cdhidalgo.tecnm.shoppit.DataClass

import android.os.Parcel
import android.os.Parcelable

data class Producto(
    var NProducto:String?,
    var Precio:String?,
    var Codigo:String?,
    var Stock:String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(NProducto)
        parcel.writeString(Precio)
        parcel.writeString(Codigo)
        parcel.writeString(Stock)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Producto> {
        override fun createFromParcel(parcel: Parcel): Producto {
            return Producto(parcel)
        }

        override fun newArray(size: Int): Array<Producto?> {
            return arrayOfNulls(size)
        }
    }
}
