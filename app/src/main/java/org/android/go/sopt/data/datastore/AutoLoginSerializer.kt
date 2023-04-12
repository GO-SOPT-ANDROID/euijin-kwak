package org.android.go.sopt.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import org.android.go.sopt.AutoLogin
import java.io.InputStream
import java.io.OutputStream

object AutoLoginSerializer : Serializer<AutoLogin> {
    override val defaultValue: AutoLogin = AutoLogin.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): AutoLogin {
        try {
            return AutoLogin.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: AutoLogin, output: OutputStream) = t.writeTo(output)
}