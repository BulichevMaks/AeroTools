package com.formgrav.aerotools.data.datasourse

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.os.Handler

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.formgrav.aerotools.R

import com.formgrav.aerotools.data.model.AttitudeDataSourse
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import java.io.IOException
import java.nio.charset.Charset

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext


class ArduinoClientImpl(private val mainHandler: Handler, private val context: Context) {

    private var usbManager: UsbManager =
        context.applicationContext.getSystemService(Context.USB_SERVICE) as UsbManager
    private lateinit var device: UsbDevice
    private lateinit var serialPort: UsbSerialPort
    private lateinit var connection: UsbDeviceConnection
    private var isSerialConnectionOpen = false
    private var receiveBuffer = StringBuilder()

    private val scope = CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, exception ->
        Log.e("MY_LOG", "Coroutine error: $exception")
    })
    private val _counLiveData = MutableLiveData<AttitudeDataSourse>()
    val counLiveData: LiveData<AttitudeDataSourse> = _counLiveData

    private val mCallback = object : Runnable {
        override fun run() {
            val buffer = ByteArray(64)
            while (isSerialConnectionOpen) {
                try {
                    val bytesRead = serialPort.read(buffer, 2000)
                    if (bytesRead > 0) {
                        val rawData = String(buffer, 0, bytesRead, Charset.defaultCharset())

                        receiveBuffer.append(rawData) // Добавляем данные в буфер

                        if (receiveBuffer.contains(">")) { // Проверяем наличие разделителя
                            val endIndex = receiveBuffer.indexOf(">")
                            val fullData = receiveBuffer.substring(
                                0,
                                endIndex
                            ) // Получаем полные данные до разделителя
                           // Log.d("MY_LOG", "Received data: $fullData")
                            receiveBuffer.delete(
                                0,
                                endIndex + 1
                            ) // Удаляем полные данные и разделитель из буфера
                            //  Log.d("MY_LOG", "Received data: $fullData")
                            scope.launch {
                                _counLiveData.postValue(
                                    AttitudeDataSourse(
                                        altitude = "",
                                        pressure = fullData,
                                        roll = "",
                                        pitch = "",
                                    )
                                )
                            }
                        }

                        if (receiveBuffer.contains("_")) { // Проверяем наличие разделителя
                            val endIndex = receiveBuffer.indexOf("_")
                            val fullData = receiveBuffer.substring(
                                0,
                                endIndex
                            ) // Получаем полные данные до разделителя
                            receiveBuffer.delete(
                                0,
                                endIndex + 1
                            ) // Удаляем полные данные и разделитель из буфера
                            //  Log.d("MY_LOG", "Received data: $fullData")
                            scope.launch {
                                _counLiveData.postValue(
                                    AttitudeDataSourse(
                                        altitude = fullData,
                                        pressure = "",
                                        roll = "",
                                        pitch = "",
                                    )
                                )
                            }
                        }

                        if (receiveBuffer.contains("<")) {
                            val endIndex = receiveBuffer.indexOf("<")
                            val fullData = receiveBuffer.substring(0, endIndex)
                            receiveBuffer.delete(0, endIndex + 1)

                            val values = fullData.split(",")

                            if (values.size >= 3) {
                                val roll = values[1].toDoubleOrNull()
                                val pitch = values[2].toDoubleOrNull()

                                if (roll != null && pitch != null) {
                                  //  Log.d("MY_LOG", "Received data - Roll: $roll, Pitch: $pitch")
                                    scope.launch {
                                        _counLiveData.postValue(
                                            AttitudeDataSourse(
                                                altitude = "",
                                                pressure = "",
                                                roll = roll.toString(),
                                                pitch = pitch.toString(),
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                } catch (e: IOException) {
                    Log.d("MY_LOG", "The device is disabled")
                    MaterialAlertDialogBuilder(context)
                        .setMessage("PORT NOT OPEN")
                        .setNeutralButton(
                            "ОК"
                        ) { dialog, which ->

                        }.show()
                    e.printStackTrace()
                    // return
                }
            }
        }
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //   Log.d("MY_LOG", "onReceive")
            if (intent.action == ACTION_USB_PERMISSION) {
                val granted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, true)
                if (granted) {
                    connection = usbManager.openDevice(device)
                    if (connection == null) {

                        //          Log.d("MY_LOG", "устройство не удалось открыть")
                        mainHandler.post {
                            Toast.makeText(context, "устройство не удалось открыть", Toast.LENGTH_LONG).show()
                        }


                    } else {

                        //     Log.d("MY_LOG", "удалось открыть устройство")
//                        mainHandler.post {
//                            Toast.makeText(context, "удалось открыть устройство", Toast.LENGTH_LONG).show()
//                        }

                    }
                    val driver = UsbSerialProber.getDefaultProber().probeDevice(device)
                    if (driver != null) {
                        serialPort = driver.ports[0]
                        try {
                            serialPort.open(connection)
                            serialPort.setParameters(
                                115200,
                                UsbSerialPort.DATABITS_8,
                                UsbSerialPort.STOPBITS_1,
                                UsbSerialPort.PARITY_NONE
                            )
                            isSerialConnectionOpen = true
                            scope.launch {
                                mCallback.run()
                            }
                        } catch (e: Exception) {
                            //           Log.d("MY_LOG", "PORT NOT OPEN")
                            mainHandler.post {
                                Toast.makeText(context, "PORT NOT OPEN", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        //       Log.d("MY_LOG", "Driver not found")
                        mainHandler.post {
                            Toast.makeText(context, "Driver not found", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    //      Log.d("MY_LOG", "PERM NOT GRANTED")
                    mainHandler.post {
                        Toast.makeText(context, "PERM NOT GRANTED", Toast.LENGTH_LONG).show()
                    }
                }
            } else if (intent.action == UsbManager.ACTION_USB_DEVICE_ATTACHED) {

                onClickStart()
            } else if (intent.action == UsbManager.ACTION_USB_DEVICE_DETACHED) {
                // Действия при отключении USB-устройства
                closeConnection()
                Log.d("MY_LOG", "The device is disabled")
                mainHandler.post {
                    Toast.makeText(context, "The device is disabled", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    init {
        val filter = IntentFilter()
        filter.addAction(ACTION_USB_PERMISSION)
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        context.registerReceiver(broadcastReceiver, filter)
    }

    fun startAltFlow() {
        onClickStart()
      //  Log.d("MY_LOG1", "${counLiveData.value}")
    }

    fun setNewPressure(p: String) {
      //  Log.d("MY_LOG", "Received data: $p")
        serialPort.write(p.toByteArray(), 2000) //
    }

    fun stopAltFlow() {
        closeConnection()
    }

    fun isSerialConnectionOpen(): Boolean = isSerialConnectionOpen

    private fun closeConnection() {
        if (isSerialConnectionOpen) {
            serialPort.close()
            isSerialConnectionOpen = false
            context.unregisterReceiver(broadcastReceiver)
        }
    }

    companion object {
        private const val ACTION_USB_PERMISSION = "com.formgrav.aerotools.USB_PERMISSION"
    }

    fun onClickStart() {
        val usbDevices: HashMap<String, UsbDevice> = usbManager.deviceList
        if (usbDevices.isNotEmpty()) {
            var keep = true
            for ((_, value) in usbDevices) {
                device = value
                val deviceVID: Int = device.vendorId
                if (deviceVID == 0x2341) {
                    val pi: PendingIntent = PendingIntent.getBroadcast(
                        context,
                        0,
                        Intent(ACTION_USB_PERMISSION),
                        PendingIntent.FLAG_IMMUTABLE
                    )
                    usbManager.requestPermission(device, pi)
                    keep = false
                } else {
                    connection = usbManager.openDevice(device) ?: error("Device connection is null")
                    //     Log.d("MY_LOG", "Device connection is null")
                }
                if (!keep) break
            }
        }
    }
}

