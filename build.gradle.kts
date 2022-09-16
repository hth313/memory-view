//import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
    kotlin("js") version "1.7.10"
}

group = "org.example"
version = "0.9.0"

repositories {
    mavenCentral()
}

kotlin {
    js {
        browser {
        }
        binaries.executable()
    }
    val doodleVersion = "0.8.1" // <--- Latest Doodle version

    dependencies {
        implementation ("io.nacular.doodle:core:$doodleVersion"   )
        implementation ("io.nacular.doodle:browser:$doodleVersion")

        // Optional
        // implementation ("io.nacular.doodle:controls:$doodleVersion" )
        // implementation ("io.nacular.doodle:animation:$doodleVersion")
        // implementation ("io.nacular.doodle:themes:$doodleVersion"   )
    }
}


//dependencies {
//    testImplementation(kotlin("test"))
//}

//tasks.test {
//    useJUnitPlatform()
//}

//tasks.withType<KotlinCompile> {
//    kotlinOptions.jvmTarget = "1.8"
//}