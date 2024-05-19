package com.example.github

import android.content.Intent
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourprojectname.RepoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var searchButton: Button
    lateinit var searchEditText: EditText
    lateinit var recyclerView: RecyclerView
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val service = retrofit.create(GitHubService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton = findViewById(R.id.search_button)
        searchEditText = findViewById(R.id.search_edit_text)
        recyclerView = findViewById(R.id.recycler_view)




        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            service.searchRepositories(query).enqueue(object : Callback<RepoSearchResponse> {
                override fun onResponse(call: Call<RepoSearchResponse>, response: Response<RepoSearchResponse>) {
                    val repos = response.body()?.items.orEmpty()

                    recyclerView.adapter = RepoAdapter { repos ->
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra("url", repos)
                        startActivity(intent)
                    }

                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                }

                override fun onFailure(call: Call<RepoSearchResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
}

class DetailActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val url = intent.getStringExtra("url")
        val webView: WebView = findViewById(R.id.webView)
        if (url != null) {
            webView.loadUrl(url)
        }
    }
}
