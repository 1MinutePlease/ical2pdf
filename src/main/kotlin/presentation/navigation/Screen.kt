package presentation.navigation

sealed class Screen(val route: String) {
    object ChooseFile: Screen("/choose_file")
    object ChooseEvents: Screen("/choose_events")
    object GroupEvents: Screen("/group_events")
    object GeneratePdf: Screen("/generate_pdf")
}