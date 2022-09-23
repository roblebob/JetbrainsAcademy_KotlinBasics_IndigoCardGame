class Application(val name: String) {

    // write the run method here
    fun run(s1: String, s2: String, s3: String) = println("$name\n$s1\n$s2\n$s3")
}


class Application2(val name: String)

fun Application2.run(s1: String, s2: String, s3: String) = println("$name\n$s1\n$s2\n$s3")

