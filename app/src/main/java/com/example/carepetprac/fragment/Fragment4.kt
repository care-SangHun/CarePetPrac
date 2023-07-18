package com.example.carepetprac.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.SystemClock
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.carepetprac.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Fragment4 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var chronometer: Chronometer
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button
    private lateinit var pictureButton: Button

    private var isRunning: Boolean = false
    private var pauseOffset: Long = 0L

    private val handler = Handler()
    private val runnable = Runnable { updateTimer() }

    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private val CAMERA_REQUEST_CODE = 100
    private var currentPhotoPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_4, container, false)

        chronometer = view.findViewById(R.id.chronometer)
        startButton = view.findViewById(R.id.button_start)
        pauseButton = view.findViewById(R.id.button_pause)
        stopButton = view.findViewById(R.id.button_stop)
        pictureButton = view.findViewById(R.id.button_picture)

        startButton.setOnClickListener {startTimer()}
        pauseButton.setOnClickListener {pauseTimer()}
        stopButton.setOnClickListener {stopTimer()}
        pictureButton.setOnClickListener {takePicture()}

        return view
    }

    private fun startTimer() {
        if (!isRunning) {
            // 크로노미터의 베이스 = 시스템 시작이후 흐른 시간 - 멈춰 있던 시간
            // Pause 누른다고 시스템 시간이 멈추지않아서 필요한 작업
            chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
            chronometer.start()
            isRunning = true
            handler.postDelayed(runnable, 1000)
        }
    }

    private fun pauseTimer() {
        if (isRunning) {
            chronometer.stop()
            // Pause를 누를때마다 시스템시간이랑 차이가 생겨서 pauseOffset을 계속 갱신해줘야함
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
            isRunning = false
            handler.removeCallbacks(runnable)
        }
    }

    private fun stopTimer() {
        chronometer.stop()
        chronometer.base = SystemClock.elapsedRealtime()
        pauseOffset = 0L
        isRunning = false
        handler.removeCallbacks(runnable)
        Toast.makeText(requireContext(), chronometer.base.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun takePicture() {
        dispatchTakePictureIntent()
        Toast.makeText(requireContext(), "Taking picture...", Toast.LENGTH_SHORT).show()
        // Picture capture logic here

    }

    private fun updateTimer() {
        if (isRunning) {
            val elapsedMillis = SystemClock.elapsedRealtime() - chronometer.base
            val elapsedSeconds = (elapsedMillis / 1000).toInt()
            // Update UI or perform any other actions based on elapsed time
            handler.postDelayed(runnable, 1000)
        }
    }

    private fun dispatchTakePictureIntent() {
        requestCameraPermission()
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            val photoFile: File? = createImageFile()
            photoFile?.let {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.android.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
            }
        } catch (ex: IOException) {
            // 파일 생성 실패 시 처리 로직
        }
    }

    // 이미지 파일 생성
    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    // 카메라 촬영 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            currentPhotoPath?.let { path ->
                // 이미지 파일의 경로를 사용하여 원하는 작업 수행
                // 예: 이미지 파일을 업로드하거나, 이미지를 표시하는 등의 동작
                Toast.makeText(requireContext(), "Image saved at: $path", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            // 이미 퍼미션이 허용된 경우
        }
    }

    // 퍼미션 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 퍼미션이 허용된 경우
                startCamera()
            } else {
                // 퍼미션이 거부된 경우
                Toast.makeText(requireContext(), "카메라 퍼미션이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCamera() {
        // 카메라 사용을 위한 코드 작성
        // ...
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment4().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}