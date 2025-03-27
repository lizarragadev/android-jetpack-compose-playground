package com.droidcon.composablebank.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.droidcon.composablebank.ui.adaptive_ui.*
import com.droidcon.composablebank.ui.animations_effects.*
import com.droidcon.composablebank.ui.feedback_loading.*
import com.droidcon.composablebank.ui.input_controls.*
import com.droidcon.composablebank.ui.layout.*
import com.droidcon.composablebank.ui.navigation.*

object DataProvider {
    private val allComposables by lazy {
        listOf(
            feedbackComposables,
            navigationComposables,
            layoutComposables,
            inputComposables,
            animationComposables,
            adaptiveUiComposables
        ).flatten()
    }

    val composables: List<ComposableItem>
        @RequiresApi(Build.VERSION_CODES.O)
        get() = allComposables

    private const val CATEGORY_FEEDBACK = "Feedback & Loading"
    private const val CATEGORY_NAVIGATION = "Navigation"
    private const val CATEGORY_LAYOUT = "Layout"
    private const val CATEGORY_INPUT = "Input Controls"
    private const val CATEGORY_ANIMATION = "Animations & Effects"
    private const val CATEGORY_ADAPTIVE = "Adaptive UI"

    private val feedbackComposables = listOf(
        createSnackbar(),
        createAlertDialog(),
        createToast(),
        createCircularProgress(),
        createLinearProgress()
    )

    private val navigationComposables = listOf(
        createTopAppBar(),
        createBottomNavigation(),
        createBottomAppBar(),
        createNavigationRail(),
        createTabs()
    )

    private val layoutComposables = listOf(
        createRow(),
        createColumn(),
        createBox(),
        createConstraintLayout()
    )

    private val inputComposables = listOf(
        createTextField(),
        createButton(),
        createSlider(),
        createSwitch(),
        createCheckbox(),
        createRadioButton(),
        createSearchBar()
    )

    private val animationComposables = listOf(
        createAnimatedVisibility(),
        createAnimatedContent(),
        createCrossfade(),
        createBlurEffect(),
        createShadow(),
        createClip()
    )

    private val adaptiveUiComposables = listOf(
        createWindowSizeClass(),
        createLocalConfiguration()
    )

    private fun createSnackbar() = ComposableItem(
        name = "Snackbar",
        description = "A component that displays a brief message at the bottom...",
        category = CATEGORY_FEEDBACK,
        commonUse = "Temporary notifications that do not interrupt the user experience.",  // <-- Añadido
        properties = listOf(
            "modifier: Modifier = Modifier",
            "action: @Composable (() -> Unit)? = null",
            "dismissAction: @Composable (() -> Unit)? = null",
            "shape: Shape = SnackbarDefaults.shape",
            "containerColor: Color = SnackbarDefaults.color",
            "contentColor: Color = SnackbarDefaults.contentColor",
            "content: @Composable (() -> Unit)"
        ),
        demo = { navController, name -> Snackbar(navController, name) }
    )

    private fun createAlertDialog() = ComposableItem(
        name = "AlertDialog",
        description = "A pop-up window that displays an important message...",
        category = CATEGORY_FEEDBACK,
        commonUse = "Confirmations, warnings or critical system messages.",  // <-- Añadido
        properties = listOf(
            "onDismissRequest: () -> Unit",
            "title: @Composable (() -> Unit)? = null",
            "text: @Composable (() -> Unit)? = null",
            "confirmButton: @Composable (() -> Unit)",
            "dismissButton: @Composable (() -> Unit)? = null",
            "properties: DialogProperties = DialogProperties()",
            "modifier: Modifier = Modifier",
            "icon: @Composable (() -> Unit)? = null"
        ),
        demo = { navController, name -> AlertDialog(navController, name) }
    )

    private fun createToast() = ComposableItem(
        name = "Toast",
        description = "A brief and temporary message that appears on the screen...",
        category = CATEGORY_FEEDBACK,
        commonUse = "Informative messages that do not require user interaction.",
        properties = listOf(
            "context: Context",
            "message: CharSequence",
            "duration: ToastDuration"
        ),
        demo = { navController, name -> Toast(navController, name) }
    )

    private fun createCircularProgress() = ComposableItem(
        name = "CircularProgressIndicator",
        description = "A circular progress indicator that shows the status...",
        category = CATEGORY_FEEDBACK,
        commonUse = "Data loading, background processing.",
        properties = listOf(
            "progress: () -> Float",
            "modifier: Modifier = Modifier",
            "color: Color = ProgressIndicatorDefaults.circularColor",
            "strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth"
        ),
        demo = { navController, name -> CircularProgressIndicator(navController, name) }
    )

    private fun createLinearProgress() = ComposableItem(
        name = "LinearProgressIndicator",
        description = "Similar to CircularProgressIndicator, but in the form...",
        category = CATEGORY_FEEDBACK,
        commonUse = "Step-by-step forms, loading bars.",
        properties = listOf(
            "progress: () -> Float",
            "modifier: Modifier = Modifier",
            "color: Color = ProgressIndicatorDefaults.linearColor",
            "trackColor: Color = ProgressIndicatorDefaults.linearTrackColor",
            "strokeCap: StrokeCap = ProgressIndicatorDefaults.LinearStrokeCap",
            "gapSize: Dp = ProgressIndicatorDefaults.LinearIndicatorTrackGapSize"
        ),
        demo = { navController, name -> LinearProgressIndicator(navController, name) }
    )

    private fun createTopAppBar() = ComposableItem(
        name = "TopAppBar",
        description = "A top navigation bar that generally contains...",
        category = CATEGORY_NAVIGATION,
        commonUse = "Screen headers, hierarchical navigation.",
        properties = listOf(
            "title: @Composable (() -> Unit)",
            "modifier: Modifier = Modifier",
            "navigationIcon: @Composable (() -> Unit) = {}",
            "actions: @Composable (RowScope.() -> Unit) = {}",
            "colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()",
            "scrollBehavior: TopAppBarScrollBehavior? = null"
        ),
        demo = { navController, name -> TopAppBar(navController, name) }
    )

    private fun createBottomNavigation() = ComposableItem(
        name = "BottomNavigationBar",
        description = "A bottom navigation bar that allows...",
        category = CATEGORY_NAVIGATION,
        commonUse = "Primary navigation in mobile apps.",
        properties = listOf(
            "modifier: Modifier = Modifier",
            "containerColor: Color = NavigationBarDefaults.containerColor",
            "contentColor: Color = MaterialTheme.colorScheme.contentColorFor(containerColor)",
            "tonalElevation: Dp = NavigationBarDefaults.Elevation"
        ),
        demo = { navController, name -> BottomNavigationBar(navController, name) }
    )

    private fun createBottomAppBar() = ComposableItem(
        name = "BottomAppBar",
        description = "The Bottom App Bar in Material 3...",
        category = CATEGORY_NAVIGATION,
        commonUse = "Primary navigation in mobile apps.",
        properties = listOf(
            "modifier: Modifier = Modifier",
            "containerColor: Color = BottomAppBarDefaults.containerColor",
            "contentColor: Color = contentColorFor(containerColor)",
            "tonalElevation: Dp = BottomAppBarDefaults.ContainerElevation",
            "contentPadding: PaddingValues = BottomAppBarDefaults.ContentPadding",
            "windowInsets: WindowInsets = BottomAppBarDefaults.windowInsets"
        ),
        demo = { navController, name -> BottomAppBar(navController, name) }
    )

    private fun createNavigationRail() = ComposableItem(
        name = "NavigationRail",
        description = "A vertical navigation bar used on larger-screen devices...",
        category = CATEGORY_NAVIGATION,
        commonUse = "Adaptive designs for tablets and large screens.",
        properties = listOf(
            "modifier: Modifier = Modifier",
            "containerColor: Color = NavigationRailDefaults.ContainerColor",
            "contentColor: Color = contentColorFor(containerColor)",
            "header: @Composable (ColumnScope.() -> Unit)? = null"
        ),
        demo = { navController, name -> NavigationRail(navController, name) }
    )

    private fun createTabs() = ComposableItem(
        name = "Tabs",
        description = "Horizontal tabs that organize content into categories...",
        category = CATEGORY_NAVIGATION,
        commonUse = "Organizing related content, such as browser tabs.",
        properties = listOf(
            "selected: Boolean",
            "onClick: () -> Unit",
            "modifier: Modifier = Modifier",
            "enabled: Boolean = true",
            "text: @Composable (() -> Unit)? = null",
            "icon: @Composable (() -> Unit)? = null",
            "selectedContentColor: Color = LocalContentColor.current",
            "unselectedContentColor: Color = selectedContentColor"
        ),
        demo = { navController, name -> Tabs(navController, name) }
    )

    private fun createRow() = ComposableItem(
        name = "Row",
        description = "A container that organizes its child elements...",
        category = CATEGORY_LAYOUT,
        commonUse = "Horizontal layouts, toolbars.",
        properties = listOf(
            "horizontalArrangement: Arrangement.Horizontal",
            "verticalAlignment: Alignment.Vertical",
            "modifier: Modifier",
            "content: @Composable () -> Unit"
        ),
        demo = { navController, name -> RowLayout(navController, name) }
    )

    private fun createColumn() = ComposableItem(
        name = "Column",
        description = "A container that organizes its child elements...",
        category = CATEGORY_LAYOUT,
        commonUse = "Vertical layouts, lists of elements.",
        properties = listOf(
            "modifier: Modifier = Modifier",
            "verticalArrangement: Arrangement.Vertical = Arrangement.Top",
            "horizontalAlignment: Alignment.Horizontal = Alignment.Start"
        ),
        demo = { navController, name -> ColumnLayout(navController, name) }
    )

    private fun createBox() = ComposableItem(
        name = "Box",
        description = "Stacking container for overlaying elements.",
        category = CATEGORY_LAYOUT,
        commonUse = "Layered layouts and absolute positioning.",
        properties = listOf(
            "modifier: Modifier = Modifier",
            "contentAlignment: Alignment = Alignment.TopStart"
        ),
        demo = { navController, name -> BoxLayout(navController, name) }
    )

    private fun createConstraintLayout() = ComposableItem(
        name = "ConstraintLayout",
        description = "Advanced layout with constraints.",
        category = CATEGORY_LAYOUT,
        commonUse = "Complex responsive layouts.",
        properties = listOf(
            "constraintSet: ConstraintSet",
            "modifier: Modifier = Modifier"
        ),
        demo = { navController, name -> ConstraintLayout(navController, name) }
    )

    private fun createTextField() = ComposableItem(
        name = "TextField",
        description = "User input field for text entry.",
        category = CATEGORY_INPUT,
        commonUse = "Forms, search bars, data entry.",
        properties = listOf(
            "value: String",
            "onValueChange: (String) -> Unit",
            "modifier: Modifier = Modifier",
            "label: @Composable (() -> Unit)? = null",
            "colors: TextFieldColors = TextFieldDefaults.colors()",
            "readOnly: Boolean = false",
            "leadingIcon: @Composable (() -> Unit)? = null",
            "supportingText: @Composable (() -> Unit)? = null",
            "isError: Boolean = false",
            "maxLines: Int = if (singleLine) 1 else Int. MAX_VALUE",
            "visualTransformation: VisualTransformation = VisualTransformation.None",
            "keyboardOptions: KeyboardOptions = KeyboardOptions.Default",
            "trailingIcon: @Composable (() -> Unit)? = null",
            "enabled: Boolean = true",
            "singleLine: Boolean = false",
            "prefix: @Composable (() -> Unit)? = null",
            "suffix: @Composable (() -> Unit)? = null",
            "textStyle: TextStyle = LocalTextStyle.current",
        ),
        demo = { navController, name -> TextFields(navController, name) }
    )

    private fun createButton() = ComposableItem(
        name = "Button",
        description = "Interactive element for user actions.",
        category = CATEGORY_INPUT,
        commonUse = "Triggering primary actions.",
        properties = listOf(
            "onClick: () -> Unit",
            "modifier: Modifier = Modifier",
            "enabled: Boolean = true",
            "shape: Shape = ButtonDefaults.shape",
            "colors: ButtonColors = ButtonDefaults.buttonColors()",
            "elevation: ButtonElevation? = ButtonDefaults.buttonElevation()"
        ),
        demo = { navController, name -> Buttons(navController, name) }
    )

    private fun createSlider() = ComposableItem(
        name = "Slider",
        description = "Range selector for value input.",
        category = CATEGORY_INPUT,
        commonUse = "Selecting values within a range.",
        properties = listOf(
            "value: Float",
            "onValueChange: (Float) -> Unit",
            "valueRange: ClosedFloatingPointRange<Float> = 0f..1f",
            "colors: SliderColors = SliderDefaults.colors()",
            "enabled: Boolean = true",
            "@IntRange(from = 0) steps: Int = 0",
            "modifier: Modifier = Modifier"
        ),
        demo = { navController, name -> Sliders(navController, name) }
    )

    private fun createSwitch() = ComposableItem(
        name = "Switch",
        description = "Binary toggle switch control.",
        category = CATEGORY_INPUT,
        commonUse = "Enabling/disabling features.",
        properties = listOf(
            "checked: Boolean",
            "onCheckedChange: ((Boolean) -> Unit)?",
            "modifier: Modifier = Modifier",
            "thumbContent: @Composable (() -> Unit)? = null",
            "enabled: Boolean = true",
            "colors: SwitchColors = SwitchDefaults.colors()"
        ),
        demo = { navController, name -> Switches(navController, name) }
    )

    private fun createCheckbox() = ComposableItem(
        name = "Checkbox",
        description = "Multi-selection control.",
        category = CATEGORY_INPUT,
        commonUse = "Selecting multiple options.",
        properties = listOf(
            "checked: Boolean",
            "onCheckedChange: ((Boolean) -> Unit)?",
            "modifier: Modifier = Modifier",
            "enabled: Boolean = true",
            "colors: CheckboxColors = CheckboxDefaults.colors()"
        ),
        demo = { navController, name -> Checkboxes(navController, name) }
    )

    private fun createRadioButton() = ComposableItem(
        name = "RadioButton",
        description = "Single-selection control.",
        category = CATEGORY_INPUT,
        commonUse = "Choosing one option from many.",
        properties = listOf(
            "selected: Boolean",
            "onClick: (() -> Unit)?",
            "modifier: Modifier = Modifier",
            "enabled: Boolean = true",
            "colors: RadioButtonColors = RadioButtonDefaults.colors()"
        ),
        demo = { navController, name -> RadioButtons(navController, name) }
    )

    private fun createSearchBar() = ComposableItem(
        name = "SearchBar",
        description = "Input field for search queries.",
        category = CATEGORY_INPUT,
        commonUse = "Content filtering and searching.",
        properties = listOf(
            "expanded: Boolean",
            "onExpandedChange: (Boolean) -> Unit",
            "inputField: @Composable (() -> Unit)",
            "modifier: Modifier = Modifier",
            "shape: Shape = SearchBarDefaults.inputFieldShape",
            "colors: SearchBarColors = SearchBarDefaults.colors()",
            "tonalElevation: Dp = SearchBarDefaults.TonalElevation",
            "shadowElevation: Dp = SearchBarDefaults.ShadowElevation",
            "windowInsets: WindowInsets = SearchBarDefaults.windowInsets"
        ),
        demo = { navController, name -> SearchBars(navController, name) }
    )

    private fun createAnimatedVisibility() = ComposableItem(
        name = "AnimatedVisibility",
        description = "A component that shows or hides content with smooth animations.",
        category = CATEGORY_ANIMATION,
        commonUse = "Dynamically showing/hiding elements with transitions.",
        properties = listOf(
            "visible: Boolean",
            "modifier: Modifier = Modifier",
            "enter: EnterTransition = fadeIn() + expandIn()",
            "exit: ExitTransition = shrinkOut() + fadeOut()"
        ),
        demo = { navController, name -> AnimatedVisibility(navController, name) }
    )

    private fun createAnimatedContent() = ComposableItem(
        name = "AnimatedContent",
        description = "Animates transitions between different contents.",
        category = CATEGORY_ANIMATION,
        commonUse = "Smooth transitions between view states.",
        properties = listOf(
            "targetState: S",
            "modifier: Modifier = Modifier",
            "transitionSpec: AnimatedContentTransitionScope<S>.() -> ContentTransform",
            "label: String = \"AnimatedContent\""
        ),
        demo = { navController, name -> AnimatedContent(navController, name) }
    )

    private fun createCrossfade() = ComposableItem(
        name = "Crossfade",
        description = "Transition effect that fades between elements.",
        category = CATEGORY_ANIMATION,
        commonUse = "Smooth content replacement animations.",
        properties = listOf(
            "targetState: T",
            "modifier: Modifier = Modifier",
            "animationSpec: FiniteAnimationSpec<Float> = tween()",
            "label: String = \"Crossfade\""
        ),
        demo = { navController, name -> Crossfade(navController, name) }
    )

    private fun createBlurEffect() = ComposableItem(
        name = "BlurEffect",
        description = "Applies blur effect to content.",
        category = CATEGORY_ANIMATION,
        commonUse = "Creating depth and focus effects.",
        properties = listOf(
            "radiusX: Dp",
            "radiusY: Dp",
            "edgeTreatment: BlurredEdgeTreatment = BlurredEdgeTreatment.Rectangle"
        ),
        demo = { navController, name -> BlurEffect(navController, name) }
    )

    private fun createShadow() = ComposableItem(
        name = "Shadow",
        description = "Adds depth with shadow effects.",
        category = CATEGORY_ANIMATION,
        commonUse = "Elevation effects for UI elements.",
        properties = listOf(
            "elevation: Dp",
            "shape: Shape = RectangleShape",
            "clip: Boolean = elevation > 0.dp",
            "ambientColor: Color = DefaultShadowColor",
            "spotColor: Color = DefaultShadowColor"
        ),
        demo = { navController, name -> Shadow(navController, name) }
    )

    private fun createClip() = ComposableItem(
        name = "Clip",
        description = "Clips content to specific shapes.",
        category = CATEGORY_ANIMATION,
        commonUse = "Creating rounded corners and custom shapes.",
        properties = listOf(
            "shape: Shape"
        ),
        demo = { navController, name -> Clip(navController, name) }
    )

    private fun createWindowSizeClass() = ComposableItem(
        name = "WindowSizeClass",
        description = "A component that adapts the user interface based...",
        category = CATEGORY_ADAPTIVE,
        commonUse = "Adaptive designs, multi-platform compatibility.",
        properties = listOf(
            "windowSizeClass: WindowSizeClass"
        ),
        demo = { navController, name -> WindowSizeClass(navController, name) }
    )

    private fun createLocalConfiguration() = ComposableItem(
        name = "LocalConfiguration",
        description = "A component that accesses device configuration...",
        category = CATEGORY_ADAPTIVE,
        commonUse = "Adaptation to different orientations and screen sizes.",
        properties = listOf(
            "LocalConfiguration.current",
            "configuration.screenWidthDp.dp",
            "configuration.screenHeightDp.dp",
            "configuration.touchscreen",
            "configuration.keyboard",
            "configuration.navigation"
        ),
        demo = { navController, name -> LocalConfiguration(navController, name) }
    )

}