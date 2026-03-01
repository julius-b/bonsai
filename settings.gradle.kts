rootProject.name = "bonsai"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(
    ":sample",
    ":bonsai-core",
    ":bonsai-file-system",
    ":bonsai-json",
)
