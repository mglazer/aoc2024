object Utils {
    fun load(path: String): String {
        return Utils.javaClass.getResource(path).readText()
    }
}