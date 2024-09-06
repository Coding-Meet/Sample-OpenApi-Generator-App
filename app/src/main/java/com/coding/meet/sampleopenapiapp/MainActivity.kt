package com.coding.meet.sampleopenapiapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.coding.meet.sampleopenapiapp.apis.PostsApi
import com.coding.meet.sampleopenapiapp.models.Post
import com.coding.meet.sampleopenapiapp.ui.theme.SampleOpenApiAppTheme
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import io.ktor.client.call.body
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SampleOpenApiAppTheme {
                val coroutineScope = rememberCoroutineScope()
                LaunchedEffect(Unit) {
                    coroutineScope.launch(Dispatchers.IO) {
                        try {

                            // normal api calling
                            val httpClient =  HttpClient {
                                defaultRequest {
                                    url("https://jsonplaceholder.typicode.com/")
                                    contentType(ContentType.Application.Json)
                                }
                                install(HttpTimeout) {
                                    socketTimeoutMillis = 60_000
                                    requestTimeoutMillis = 60_000
                                }
                                install(ContentNegotiation) {
                                    json(Json {
                                        prettyPrint = true
                                        isLenient = true
                                        ignoreUnknownKeys = true
                                        explicitNulls = false
                                    })
                                }

                            }
                            val posts = httpClient.get{
                                url("posts")
                            }.body<List<Post>>()
                            Log.d("postList", posts.toString())


                            // open api calling
                            val httpApiClient = PostsApi(
                                "https://jsonplaceholder.typicode.com/",
                                httpClientConfig = {
                                    it.install(ContentNegotiation) {
                                        json(Json {
                                            prettyPrint = true
                                            isLenient = true
                                            ignoreUnknownKeys = true
                                            explicitNulls = false
                                        })
                                    }
                                }
                            )
                            Log.d("posts", httpApiClient.getPosts().body().toString())
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SampleOpenApiAppTheme {
        Greeting("Android")
    }
}