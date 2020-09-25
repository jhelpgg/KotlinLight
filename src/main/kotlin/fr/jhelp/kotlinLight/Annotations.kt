package fr.jhelp.kotlinLight

// @Try somethingMyFail()
// =>
// try somethingMyFail()
// ---
// @Try x = somethingMyFail()
// =>
// x = try somethingMyFail()
@Target(AnnotationTarget.EXPRESSION, AnnotationTarget.LOCAL_VARIABLE)
@Retention(AnnotationRetention.SOURCE)
annotation class Try

// @Override
// =>
// override
@Target(AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.SOURCE)
annotation class Override

// @ImportSwift("Library")
// =>
// import Library
annotation class ImportSwift(val name: String)