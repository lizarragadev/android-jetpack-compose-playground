package com.droidcon.composablebank.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import com.droidcon.composablebank.ui.adaptive_ui.*
import com.droidcon.composablebank.ui.animations_effects.*
import com.droidcon.composablebank.ui.feedback_loading.*
import com.droidcon.composablebank.ui.input_controls.*
import com.droidcon.composablebank.ui.layout.*
import com.droidcon.composablebank.ui.navigation.*

object DataProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    val composables = listOf(
        ComposableItem(
            name = "Snackbar",
            description = "A component that displays a brief message at the bottom of the screen. It is useful for providing feedback to the user after an action, such as \"Message sent\" or \"Error saving. It can include an optional action button for additional interactions.",
            category = "Feedback & Loading",
            commonUse = "Temporary notifications that do not interrupt the user experience.",
            properties = listOf(
                "modifier: Modifier = Modifier",
                "action: @Composable (() -> Unit)? = null",
                "dismissAction: @Composable (() -> Unit)? = null",
                "shape: Shape = SnackbarDefaults.shape",
                "containerColor: Color = SnackbarDefaults.color",
                "contentColor: Color = SnackbarDefaults.contentColor",
                "content: @Composable (() -> Unit)",
            ),
            demo = { navController: NavController, name: String -> Snackbar(navController, name) }
        ),
        ComposableItem(
            name = "AlertDialog",
            description = "A pop-up window that displays an important message with options for the user to make a decision. It can include a title, descriptive text, confirmation and cancel buttons, and even a custom icon. Ideal for critical situations, such as confirming data deletion.",
            category = "Feedback & Loading",
            commonUse = "Confirmations, warnings, or important messages requiring immediate attention.",
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
            demo = { navController: NavController, name: String -> AlertDialog(navController, name) }
        ),
        ComposableItem(
            name = "Toast",
            description = "A brief and temporary message that appears on the screen to provide quick feedback. Unlike Snackbar, Toast does not include actions or interaction. It is used to notify simple events, such as \"File saved\" or \"Connection lost.\"",
            category = "Feedback & Loading",
            commonUse = "Informative messages that do not require user interaction.",
            properties = listOf(
                "context: Context",
                "message: CharSequence",
                "duration: ToastDuration",
            ),
            demo = { navController: NavController, name: String -> Toast(navController, name) }
        ),
        ComposableItem(
            name = "CircularProgressIndicator",
            description = "A circular progress indicator that shows the status of an ongoing task. It can be indeterminate (when the completion time is unknown) or determinate (when the current progress is shown). Ideal for operations like downloads or file uploads.",
            category = "Feedback & Loading",
            commonUse = "Data loading, background processing.",
            properties = listOf(
                "progress: () -> Float",
                "modifier: Modifier = Modifier",
                "color: Color = ProgressIndicatorDefaults.circularColor",
                "strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth"
            ),
            demo = { navController: NavController, name: String -> CircularProgressIndicator(navController, name) }
        ),
        ComposableItem(
            name = "LinearProgressIndicator",
            description = "Similar to CircularProgressIndicator, but in the form of a horizontal bar. It shows the progress of a task in a straight line, which may be more suitable for horizontal layouts or when multiple progress bars need to be displayed.",
            category = "Feedback & Loading",
            commonUse = "Step-by-step forms, loading bars.",
            properties = listOf(
                "progress: () -> Float",
                "modifier: Modifier = Modifier",
                "color: Color = ProgressIndicatorDefaults.linearColor",
                "trackColor: Color = ProgressIndicatorDefaults.linearTrackColor",
                "strokeCap: StrokeCap = ProgressIndicatorDefaults.LinearStrokeCap",
                "gapSize: Dp = ProgressIndicatorDefaults.LinearIndicatorTrackGapSize"
            ),
            demo = { navController: NavController, name: String -> LinearProgressIndicator(navController, name) }
        ),
        ComposableItem(
            name = "TopAppBar",
            description = "A top navigation bar that generally contains the screen title, a navigation icon (such as a menu or back arrow), and additional actions (like search or settings buttons). It is essential for navigation and content organization.",
            category = "Navigation",
            commonUse = "Screen headers, hierarchical navigation.",
            properties = listOf(
                "title: @Composable (() -> Unit)",
                "modifier: Modifier = Modifier",
                "navigationIcon: @Composable (() -> Unit) = {}",
                "actions: @Composable (RowScope.() -> Unit) = {}",
                "colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()",
                "scrollBehavior: TopAppBarScrollBehavior? = null",
            ),
            demo = { navController: NavController, name: String -> TopAppBar(navController, name) }
        ),
        ComposableItem(
            name = "BottomNavigationBar",
            description = "A bottom navigation bar that allows the user to switch between different main sections or screens of the application. Each icon represents a category or destination, such as \"Home,\" \"Search,\" or \"Profile.\"",
            category = "Navigation",
            commonUse = "Primary navigation in mobile apps.",
            properties = listOf(
                "modifier: Modifier = Modifier",
                "containerColor: Color = NavigationBarDefaults.containerColor",
                "contentColor: Color = MaterialTheme.colorScheme. contentColorFor(containerColor)",
                "tonalElevation: Dp = NavigationBarDefaults.Elevation"
            ),
            demo = { navController: NavController, name: String -> BottomNavigationBar(navController, name) }
        ),
        ComposableItem(
            name = "Bottom App Bar",
            description = "The Bottom App Bar in Material 3 is a design component providing a navigation bar at the bottom of the screen, offering quick access to primary actions and navigation within an app, particularly optimized for mobile devices.",
            category = "Navigation",
            commonUse = "Primary navigation in mobile apps.",
            properties = listOf(
                "modifier: Modifier = Modifier",
                "containerColor: Color = BottomAppBarDefaults.containerColor",
                "contentColor: Color = contentColorFor(containerColor)",
                "tonalElevation: Dp = BottomAppBarDefaults.ContainerElevation",
                "contentPadding: PaddingValues = BottomAppBarDefaults.ContentPadding",
                "windowInsets: WindowInsets = BottomAppBarDefaults.windowInsets",
            ),
            demo = { navController: NavController, name: String -> BottomAppBar(navController, name) }
        ),
        ComposableItem(
            name = "NavigationRail",
            description = "A vertical navigation bar used on larger-screen devices, such as tablets. It allows switching between main destinations similarly to BottomNavigationBar but is optimized for horizontal interfaces.",
            category = "Navigation",
            commonUse = "Adaptive designs for tablets and large screens.",
            properties = listOf(
                "modifier: Modifier = Modifier",
                "containerColor: Color = NavigationRailDefaults.ContainerColor",
                "contentColor: Color = contentColorFor(containerColor)",
                "header: @Composable (ColumnScope.() -> Unit)? = null"
            ),
            demo = { navController: NavController, name: String -> NavigationRail(navController, name) }
        ),
        ComposableItem(
            name = "Tabs",
            description = "Horizontal tabs that organize content into categories or sections. They allow the user to quickly switch between different views without navigating to another screen.",
            category = "Navigation",
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
            demo = { navController: NavController, name: String -> Tabs(navController, name) }
        ),
        ComposableItem(
            name = "Row",
            description = "A container that organizes its child elements in a horizontal row. Useful for arranging elements like buttons, text, or images in a single line.",
            category = "Layout",
            commonUse = "Horizontal layouts, toolbars.",
            properties = listOf(
                "horizontalArrangement: Arrangement.Horizontal",
                "verticalAlignment: Alignment.Vertical",
                "modifier: Modifier",
                "content: @Composable () -> Unit"
            ),
            demo = { navController: NavController, name: String -> RowLayout(navController, name) }
        ),
        ComposableItem(
            name = "Column",
            description = "A container that organizes its child elements in a vertical column. Ideal for stacking elements one on top of the other, such as forms or lists.",
            category = "Layout",
            commonUse = "Vertical layouts, lists of elements.",
            properties = listOf(
                "modifier: Modifier = Modifier",
                "verticalArrangement: Arrangement.Vertical = Arrangement.Top",
                "horizontalAlignment: Alignment.Horizontal = Alignment.Start"
            ),
            demo = { navController: NavController, name: String -> ColumnLayout(navController, name) }
        ),
        ComposableItem(
            name = "Box",
            description = "A container that stacks its child elements on top of each other. Useful for overlaying elements, such as adding an icon over an image or text over a background.",
            category = "Layout",
            commonUse = "Overlaying elements, layered designs.",
            properties = listOf(
                "modifier: Modifier = Modifier",
                "contentAlignment: Alignment = Alignment.TopStart"
            ),
            demo = { navController: NavController, name: String -> BoxLayout(navController, name) }
        ),
        ComposableItem(
            name = "ConstraintLayout",
            description = "A flexible layout system that positions elements based on constraints. Ideal for creating complex interfaces where elements need to dynamically adjust based on screen size.",
            category = "Layout",
            commonUse = "Complex and adaptive layouts.",
            properties = listOf(
                "constraintSet: ConstraintSet",
                "modifier: Modifier = Modifier"
            ),
            demo = { navController: NavController, name: String -> ConstraintLayout(navController, name) }
        ),
        ComposableItem(
            name = "TextField",
            description = "A text field that allows users to input information. It can include labels, placeholders, icons, and validations. Essential for forms and data entry.",
            category = "Input Controls",
            commonUse = "Forms, searches, comments.",
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
            demo = { navController: NavController, name: String -> TextFields(navController, name) }
        ),
        ComposableItem(
            name = "Button",
            description = "An interactive element that responds to user clicks. It can have different states (enabled/disabled) and styles (colors, elevation). Fundamental for actions like \"Send,\" \"Save,\" or \"Cancel.\"",
            category = "Input Controls",
            commonUse = "Primary actions in the interface.",
            properties = listOf(
                "onClick: () -> Unit",
                "modifier: Modifier = Modifier",
                "enabled: Boolean = true",
                "shape: Shape = ButtonDefaults.shape",
                "colors: ButtonColors = ButtonDefaults.buttonColors()",
                "elevation: ButtonElevation? = ButtonDefaults.buttonElevation()"
            ),
            demo = { navController: NavController, name: String -> Buttons(navController, name) }
        ),
        ComposableItem(
            name = "Slider",
            description = "A slider control that allows the user to select a value within a specific range. Useful for settings like volume, brightness, or filters.",
            category = "Input Controls",
            commonUse = "Configuration settings, value selection.",
            properties = listOf(
                "value: Float",
                "onValueChange: (Float) -> Unit",
                "valueRange: ClosedFloatingPointRange<Float> = 0f..1f",
                "colors: SliderColors = SliderDefaults.colors()",
                "enabled: Boolean = true",
                "@IntRange(from = 0) steps: Int = 0",
                "modifier: Modifier = Modifier"
            ),
            demo = { navController: NavController, name: String -> Sliders(navController, name) }
        ),
        ComposableItem(
            name = "Switch",
            description = "A toggle switch that allows alternating between two states (on/off). Ideal for enabling or disabling functions, such as Wi-Fi or notifications.",
            category = "Input Controls",
            commonUse = "Binary configurations, activation/deactivation.",
            properties = listOf(
                "checked: Boolean",
                "onCheckedChange: ((Boolean) -> Unit)?",
                "modifier: Modifier = Modifier",
                "thumbContent: @Composable (() -> Unit)? = null",
                "enabled: Boolean = true",
                "colors: SwitchColors = SwitchDefaults.colors()"
            ),
            demo = { navController: NavController, name: String -> Switches(navController, name) }
        ),
        ComposableItem(
            name = "Checkbox",
            description = "A check box that allows the user to select one or more options from a list. Useful for forms or configurations where multiple elements can be chosen.",
            category = "Input Controls",
            commonUse = "Multi-select lists, forms.",
            properties = listOf(
                "checked: Boolean",
                "onCheckedChange: ((Boolean) -> Unit)?",
                "modifier: Modifier = Modifier",
                "enabled: Boolean = true",
                "colors: CheckboxColors = CheckboxDefaults.colors()"
            ),
            demo = { navController: NavController, name: String -> Checkboxes(navController, name) }
        ),
        ComposableItem(
            name = "RadioButton",
            description = "A radio button that allows the user to select a single option from a group. Ideal for questions with mutually exclusive answers, such as \"Yes/No\" or \"Option A/B/C.\"",
            category = "Input Controls",
            commonUse = "Selecting one option among several.",
            properties = listOf(
                "selected: Boolean",
                "onClick: (() -> Unit)?",
                "modifier: Modifier = Modifier",
                "enabled: Boolean = true",
                "colors: RadioButtonColors = RadioButtonDefaults.colors()"
            ),
            demo = { navController: NavController, name: String -> RadioButtons(navController, name) }
        ),
        ComposableItem(
            name = "SearchBar",
            description = "A search bar that allows the user to enter queries to filter or find content. It can include placeholders, icons, and suggestions.",
            category = "Input Controls",
            commonUse = "Searches in applications, content exploration.",
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
            demo = { navController: NavController, name: String -> SearchBars(navController, name) }
        ),
        ComposableItem(
            name = "AnimatedVisibility",
            description = "A component that shows or hides content with smooth animations. Useful for visual transitions that enhance the user experience.",
            category = "Animations & Effects",
            commonUse = "Dynamically showing/hiding elements.",
            properties = listOf(
                "visible: Boolean",
                "modifier: Modifier = Modifier",
                "enter: EnterTransition = fadeIn() + expandIn()",
                "exit: ExitTransition = shrinkOut() + fadeO,()"
            ),
            demo = { navController: NavController, name: String -> AnimatedVisibility(navController, name) }
        ),
        ComposableItem(
            name = "AnimatedContent",
            description = "A component that animates transitions between different contents. Ideal for smoothly switching between views or states.",
            category = "Animations & Effects",
            commonUse = "Dynamic content changes, such as carousels.",
            properties = listOf(
                "targetState: S",
                "modifier: Modifier = Modifier",
                "transitionSpec: AnimatedContentTransitionScope<S>.() -> ContentTransform = {  }",
                "label: String = \"AnimatedContent\""
            ),
            demo = { navController: NavController, name: String -> AnimatedContent(navController, name) }
        ),
        ComposableItem(
            name = "Crossfade",
            description = "A transition effect that fades out one element while fading in another. Useful for subtle changes between views or states.",
            category = "Animations & Effects",
            commonUse = "Smooth transitions between screens or states.",
            properties = listOf(
                "targetState: T",
                "modifier: Modifier = Modifier",
                "animationSpec: FiniteAnimationSpec<Float> = tween()",
                "label: String = \"Crossfade\""
            ),
            demo = { navController: NavController, name: String -> Crossfade(navController, name) }
        ),
        ComposableItem(
            name = "BlurEffect",
            description = "A visual effect that applies blur to content. Useful for creating blurred backgrounds or highlighting specific elements.",
            category = "Animations & Effects",
            commonUse = "Backgrounds, modals, visual effects.",
            properties = listOf(
                "radiusX: Dp",
                "radiusY: Dp",
                "edgeTreatment: BlurredEdgeTreatment = BlurredEdgeTreatment.Rectangl"
            ),
            demo = { navController: NavController, name: String -> BlurEffect(navController, name) }
        ),
        ComposableItem(
            name = "Shadow",
            description = "An effect that adds shadows to components. Enhances the perception of depth and improves visual appearance.",
            category = "Animations & Effects",
            commonUse = "Cards, buttons, floating elements.",
            properties = listOf(
                "elevation: Dp",
                "shape: Shape = RectangleShape",
                "clip: Boolean = elevation > 0.dp",
                "ambientColor: Color = DefaultShadowColor",
                "spotColor: Color = DefaultShadowColor"
            ),
            demo = { navController: NavController, name: String -> Shadow(navController, name) }
        ),
        ComposableItem(
            name = "Clip",
            description = "A component that clips content to a specific shape, such as a circle or rounded rectangle. Useful for creating clean and stylish designs.",
            category = "Animations & Effects",
            commonUse = "Images, cards, decorative elements.",
            properties = listOf(
                "shape: Shape"
            ),
            demo = { navController: NavController, name: String -> Clip(navController, name) }
        ),
        ComposableItem(
            name = "WindowSizeClass",
            description = "A component that adapts the user interface based on the window size. Essential for creating responsive applications that work well across different devices, from phones to tablets and large screens.",
            category = "Adaptive UI",
            commonUse = "Adaptive designs, multi-platform compatibility.",
            properties = listOf(
                "windowSizeClass: WindowSizeClass",
            ),
            demo = { navController: NavController, name: String -> WindowSizeClass(navController, name) }
        ),
        ComposableItem(
            name = "LocalConfiguration",
            description = "A component that accesses device configuration, such as orientation (portrait/landscape) or screen density. Useful for dynamically adjusting the interface based on device characteristics.",
            category = "Adaptive UI",
            commonUse = "Adaptation to different orientations and screen sizes.",
            properties = listOf(
                "LocalConfiguration.current",
                "configuration.screenWidthDp.dp",
                "configuration.screenHeightDp.dp",
                "configuration.touchscreen",
                "configuration.keyboard",
                "configuration.navigation"
            ),
            demo = { navController: NavController, name: String -> LocalConfiguration(navController, name) }
        )
    )
}