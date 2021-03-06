import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.5.31"
	id("org.jetbrains.compose") version "1.0.0"
}

group = "com.p4ddy"
version = "1.0"

repositories {
	google()
	mavenCentral()
	maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

val exposedVersion: String by project

dependencies {
	testImplementation(kotlin("test"))
	testImplementation("io.mockk:mockk:1.12.3")
	implementation(compose.desktop.currentOs)
	implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
	implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
	implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
	implementation("org.xerial:sqlite-jdbc:3.36.0.3")
	implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
}

tasks.test {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "11"
}

compose.desktop {
	application {
		mainClass = "com.p4ddy.paddycrm.plugins.MainKt"
		nativeDistributions {
			modules("java.sql")
			targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
			packageName = "Paddy-CRM"
			packageVersion = "1.0.0"
			windows {
				iconFile.set(project.file("src/main/resources/Paddy_CRM_Logo_Square.ico"))
				dirChooser = true
				menuGroup = "Paddy-CRM"
				upgradeUuid = "f502894f-3905-41e7-8fca-9c679473fbd3"
			}
		}
	}
}
