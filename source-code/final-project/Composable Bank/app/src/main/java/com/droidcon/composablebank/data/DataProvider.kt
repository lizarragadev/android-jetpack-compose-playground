package com.droidcon.composablebank.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.droidcon.composablebank.ui.adaptive_ui.*
import com.droidcon.composablebank.ui.animations_effects.*
import com.droidcon.composablebank.ui.feedback_loading.*
import com.droidcon.composablebank.ui.input_controls.*
import com.droidcon.composablebank.ui.basic_comp_layouts.*
import com.droidcon.composablebank.ui.dev_tools.ComposePreviewTool
import com.droidcon.composablebank.ui.dev_tools.LayoutInspectorTool
import com.droidcon.composablebank.ui.interaction_gestures.DraggableInteraction
import com.droidcon.composablebank.ui.interaction_gestures.GestureDetectorInteraction
import com.droidcon.composablebank.ui.navigation.*

@RequiresApi(Build.VERSION_CODES.O)
object DataProvider {
    private val allComposables by lazy {
        listOf(
            basicComLayoutsComposables,
            inputComposables,
            navigationComposables,
            feedbackComposables,
            interactionGesturesComposables,
            animationComposables,
            adaptiveUiComposables,
            devToolsComposables
        ).flatten()
    }

    val composables: List<ComposableItem>
        @RequiresApi(Build.VERSION_CODES.O)
        get() = allComposables

    private const val CATEGORY_BASIC_COMP_LAYOUTS = "Basic Components & Layouts"
    private const val CATEGORY_INPUT = "Input Controls"
    private const val CATEGORY_NAVIGATION = "Navigation"
    private const val CATEGORY_FEEDBACK = "Feedback & Loading"
    private const val CATEGORY_INTERACTION_GESTURES = "Interaction & Gestures"
    private const val CATEGORY_ANIMATION = "Animations & Effects"
    private const val CATEGORY_ADAPTIVE = "Adaptive UI"
    private const val CATEGORY_DEV_TOOLS = "Development Tools"

    private val basicComLayoutsComposables = listOf(
        createScaffold(),
        createText(),
        createImage(),
        createRow(),
        createColumn(),
        createSurface(),
        createCard(),
        createBox(),
        createLazyColumn(),
        createLazyRow(),
        createLazyVerticalGrid(),
        createConstraintLayout()
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private val inputComposables = listOf(
        createTextField(),
        createButton(),
        createSlider(),
        createSwitch(),
        createCheckbox(),
        createRadioButton(),
        createChip(),
        createDropDownMenu(),
        createDatePickers(),
        createTimePickers(),
        createSearchBar()
    )

    private val navigationComposables = listOf(
        createTopAppBar(),
        createBottomNavigation(),
        createBottomAppBar(),
        createNavigationRail(),
        createTabs(),
        createNavDrawer()
    )

    private val feedbackComposables = listOf(
        createSnackbar(),
        createAlertDialog(),
        createToast(),
        createCircularProgress(),
        createLinearProgress(),
        createBadge(),
        createTooltipBox(),
        createBottomSheetDialog()
    )

    private val interactionGesturesComposables = listOf(
        createDraggable(),
        createGestureDetector(),
    )

    private val animationComposables = listOf(
        createPager(),
        createGraphisLayer(),
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

    private val devToolsComposables = listOf(
        createComposePreview(),
        createLayoutInspector()
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createTimePickers() = ComposableItem(
        name = "TimePicker",
        description = "A Material Design component that allows users to select a time using a visual clock interface. Supports both 24-hour and AM/PM formats, with customizable colors and layout orientations.",
        category = CATEGORY_INPUT,
        commonUse = "Scheduling events, setting reminders, time-based form inputs in applications like calendar apps or booking systems.",
        properties = listOf(
            "state: TimePickerState,",
            "modifier: Modifier = Modifier,",
            "colors: TimePickerColors = TimePickerDefaults.colors(),"
        ),
        demo = { navController, name -> TimePickerControl(navController, name) }
    )

    private fun createDatePickers() = ComposableItem(
        name = "DatePicker",
        description = "An interactive date selection component that displays a calendar grid. Supports date range restrictions and visual customization to match app themes.",
        category = CATEGORY_INPUT,
        commonUse = "Event planning apps, form inputs for birthdates, reservation systems requiring date selection.",
        properties = listOf(
            "state: DatePickerState,",
            "modifier: Modifier = Modifier,",
            "dateFormatter: DatePickerFormatter = remember { DatePickerDefaults. dateFormatter() },",
            "colors: DatePickerColors = DatePickerDefaults.colors()"
        ),
        demo = { navController, name -> DatePickerControl(navController, name) }
    )

    private fun createDropDownMenu() = ComposableItem(
        name = "DropdownMenu",
        description = "A floating menu that displays a list of choices in a compact vertical layout. Automatically positions itself relative to its anchor component and closes on external interactions.",
        category = CATEGORY_INPUT,
        commonUse = "Selection filters in toolbars, context menus for list items, profile menu options in app headers.",
        properties = listOf(
            "expanded: Boolean,",
            "onDismissRequest: () -> Unit,",
            "modifier: Modifier = Modifier,",
            "offset: DpOffset = DpOffset(0.dp, 0.dp),",
            "scrollState: ScrollState = rememberScrollState(),",
            "properties: PopupProperties = DefaultMenuProperties,"
        ),
        demo = { navController, name -> DropDownMenuControl(navController, name) }
    )

    private fun createChip() = ComposableItem(
        name = "Chip",
        description = "A compact component representing complex entities like contacts or filters. Supports selection states, icons, and customizable visual feedback for user interactions.",
        category = CATEGORY_INPUT,
        commonUse = "Tag selection in email clients, filter chips in e-commerce product lists, choice indicators in settings screens.",
        properties = listOf(
            "selected: Boolean,",
            "onClick: () -> Unit,",
            "label: @Composable (() -> Unit),",
            "modifier: Modifier = Modifier,",
            "enabled: Boolean = true,",
            "leadingIcon: @Composable (() -> Unit)? = null,",
            "trailingIcon: @Composable (() -> Unit)? = null,",
            "shape: Shape = InputChipDefaults.shape,",
            "colors: SelectableChipColors = InputChipDefaults.inputChipColors(),",
            "border: BorderStroke? = InputChipDefaults.inputChipBorder(enabled, selected),",
        ),
        demo = { navController, name -> ChipControl(navController, name) }
    )

    private fun createSnackbar() = ComposableItem(
        name = "Snackbar",
        description = "A component that displays a brief message at the bottom...",
        category = CATEGORY_FEEDBACK,
        commonUse = "Temporary notifications that do not interrupt the user experience.",
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
        commonUse = "Confirmations, warnings or critical system messages.",
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

    private fun createBottomSheetDialog() = ComposableItem(
        name = "BottomSheets",
        description = "A modal dialog that animates upward from the bottom of the screen. Supports three states: expanded, half-expanded, and hidden, with draggable handles for user control.",
        category = CATEGORY_FEEDBACK,
        commonUse = "Contextual actions in note-taking apps, detailed settings in music players, multi-step forms in banking apps.",
        properties = listOf(
            "onDismissRequest: () -> Unit,",
            "modifier: Modifier = Modifier,",
            "sheetState: SheetState = rememberModalBottomSheetState(),",
            "containerColor: Color = BottomSheetDefaults.ContainerColor,",
        ),
        demo = { navController, name -> BottomSheetDialogComposable(navController, name) }
    )

    private fun createTooltipBox() = ComposableItem(
        name = "Tooltip",
        description = "A container that displays explanatory text when the user long-presses or hovers over an element. Implements Material Design guidelines for rich contextual help with customizable positioning and styling.",
        category = CATEGORY_FEEDBACK,
        commonUse = "Explaining icon meanings in toolbars, providing form field instructions, showing extended information for truncated text.",
        properties = listOf(
            "modifier: Modifier = Modifier,",
            "contentColor: Color = TooltipDefaults.plainTooltipContentColor,",
            "containerColor: Color = TooltipDefaults.plainTooltipContainerColor,",
            "content: @Composable (() -> Unit)"
        ),
        demo = { navController, name -> TooltipBoxIndicator(navController, name) }
    )

    private fun createBadge() = ComposableItem(
        name = "Badge",
        description = "A small overlapping label that indicates status, notifications, or quantitative information. Supports dynamic content updates and multiple positioning configurations relative to its anchor.",
        category = CATEGORY_FEEDBACK,
        commonUse = "Unread message counters in messaging apps, new feature indicators in app icons, inventory status in e-commerce products.",
        properties = listOf(
            "modifier: Modifier = Modifier,",
            "containerColor: Color = BadgeDefaults.containerColor,",
            "contentColor: Color = contentColorFor(containerColor),",
            "content: @Composable (RowScope.() -> Unit)? = null"
        ),
        demo = { navController, name -> BadgeIndicator(navController, name) }
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

    private fun createNavDrawer() = ComposableItem(
        name = "NavigationDrawer",
        description = "Persistent side navigation pane that remains visible alongside primary content. Optimized for seamless integration with app structure and navigation flows.",
        category = CATEGORY_NAVIGATION,
        commonUse = "Primary navigation in enterprise apps, multi-section dashboards, apps requiring constant access to core features.",
        properties = listOf(
            "drawerContent: @Composable (() -> Unit),",
            "modifier: Modifier = Modifier,",
            "drawerState: DrawerState = rememberDrawerState(DrawerValue. Closed),",
            "gesturesEnabled: Boolean = true,",
            "scrimColor: Color = DrawerDefaults.scrimColor,",
            "content: @Composable (() -> Unit)"
        ),
        demo = { navController, name -> NavDrawer(navController, name) }
    )

    private fun createGestureDetector() = ComposableItem(
        name = "GestureDetector",
        description = "A composable that recognizes common gesture patterns and provides callbacks for different interaction states. Supports multi-touch gestures and velocity tracking for natural-feeling interactions.",
        category = CATEGORY_INTERACTION_GESTURES,
        commonUse = "Implementing swipe-to-dismiss in list items, pinch-to-zoom in image viewers, double-tap interactions in social media apps.",
        properties = listOf(
            "PointerInputScope.detectTapGestures()",
            "   onDoubleTap: ((Offset) -> Unit)? = null,",
            "   onLongPress: ((Offset) -> Unit)? = null,",
            "   onPress: suspend PressGestureScope.(Offset) -> Unit = NoPressGesture,",
            "   onTap: ((Offset) -> Unit)? = null",
            "PointerInputScope.detectTransformGestures()",
            "   panZoomLock: Boolean = false",
            "   onGesture: (Offset, Offset, Float, Float) -> Unit",
            "PointerInputScope.detectDragGestures()",
            "   onDragStart: (Offset) -> Unit = { },",
            "   onDragEnd: () -> Unit = { },",
            "   onDragCancel: () -> Unit = { },",
            "   onDrag: (PointerInputChange, Offset) -> Unit"
        ),
        demo = { navController, name -> GestureDetectorInteraction(navController, name) }
    )

    private fun createDraggable() = ComposableItem(
        name = "Draggable",
        description = "A modifier that enables smooth dragging behavior along a specified axis. Provides real-time positional data and velocity information for physics-based animations.",
        category = CATEGORY_INTERACTION_GESTURES,
        commonUse = "Implementing custom sliders, creating reorderable list items, building swipeable card decks.",
        properties = listOf(
            "PointerInputScope.detectDragGestures()",
            "   onDragStart: (Offset) -> Unit = { },",
            "   onDragEnd: () -> Unit = { },",
            "   onDragCancel: () -> Unit = { },",
            "   onDrag: (PointerInputChange, Offset) -> Unit"
        ),
        demo = { navController, name -> DraggableInteraction(navController, name) }
    )

    private fun createRow() = ComposableItem(
        name = "Row",
        description = "A container that organizes its child elements...",
        category = CATEGORY_BASIC_COMP_LAYOUTS,
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
        category = CATEGORY_BASIC_COMP_LAYOUTS,
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
        category = CATEGORY_BASIC_COMP_LAYOUTS,
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
        category = CATEGORY_BASIC_COMP_LAYOUTS,
        commonUse = "Complex responsive layouts.",
        properties = listOf(
            "constraintSet: ConstraintSet",
            "modifier: Modifier = Modifier"
        ),
        demo = { navController, name -> ConstraintLayout(navController, name) }
    )

    private fun createLazyVerticalGrid() = ComposableItem(
        name = "Lazy Vertical - Horizontal Grid",
        description = "A performant grid layout that only composes and lays out visible items. Supports adaptive column sizing and custom spacing configurations for responsive designs.",
        category = CATEGORY_BASIC_COMP_LAYOUTS,
        commonUse = "Photo galleries in social apps, product grids in e-commerce, settings option panels with icon grids.",
        properties = listOf(
            "columns: GridCells,",
            "modifier: Modifier = Modifier,",
            "state: LazyGridState = rememberLazyGridState(),",
            "verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement. Top else Arrangement. Bottom,",
            "horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,",
            "rows: GridCells,",
            "reverseLayout: Boolean = false,"
        ),
        demo = { navController, name -> LazyGridContainer(navController, name) }
    )

    private fun createLazyRow() = ComposableItem(
        name = "LazyRow",
        description = "A horizontally scrolling layout that efficiently handles large datasets by only rendering visible items. Supports sticky headers and custom scroll behaviors.",
        category = CATEGORY_BASIC_COMP_LAYOUTS,
        commonUse = "Movie/TV show carousels in streaming apps, timeline views in productivity tools, horizontally scrollable tab bars.",
        properties = listOf(
            "modifier: Modifier = Modifier,",
            "state: LazyListState = rememberLazyListState(),",
            "horizontalArrangement: Arrangement.Horizontal = if (!reverseLayout) Arrangement. Start else Arrangement. End,",
            "reverseLayout: Boolean = false,",
            "userScrollEnabled: Boolean = true,",
         ),
        demo = { navController, name -> LazyRowContainer(navController, name) }
    )

    private fun createLazyColumn() = ComposableItem(
        name = "LazyColumn",
        description = "A vertically scrolling list optimized for performance with large datasets. Implements dynamic content loading and recycling for smooth scrolling experiences.",
        category = CATEGORY_BASIC_COMP_LAYOUTS,
        commonUse = "Social media feeds, contact lists in messaging apps, long-form content displays in reading applications.",
        properties = listOf(
            "modifier: Modifier = Modifier,",
            "reverseLayout: Boolean = false,",
            "state: LazyListState = rememberLazyListState(),",
            "contentPadding: PaddingValues = PaddingValues(0.dp),",
            "verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement. Top else Arrangement. Bottom,",
            "horizontalAlignment: Alignment.Horizontal = Alignment.Start,",
            "flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),",
            "userScrollEnabled: Boolean = true,",
        ),
        demo = { navController, name -> LazyColumnContainer(navController, name) }
    )

    private fun createCard() = ComposableItem(
        name = "Card",
        description = "An elevated container with rounded corners that groups related content. Supports dynamic elevation changes and multiple content slots for complex layouts.",
        category = CATEGORY_BASIC_COMP_LAYOUTS,
        commonUse = "Product cards in e-commerce apps, article previews in news readers, user profile summaries in social networks.",
        properties = listOf(
            "onClick: () -> Unit,",
            "modifier: Modifier = Modifier,",
            "shape: Shape = CardDefaults.shape,",
            "colors: CardColors = CardDefaults.cardColors(),",
            "elevation: CardElevation = CardDefaults.cardElevation(),",
            "border: BorderStroke? = null,",
        ),
        demo = { navController, name -> CardComposable(navController, name) }
    )

    private fun createSurface() = ComposableItem(
        name = "Surface",
        description = "A foundational layout container that provides styling through elevation and shape. Serves as the base for Material Design components with theme-aware color properties.",
        category = CATEGORY_BASIC_COMP_LAYOUTS,
        commonUse = "Creating contrast areas in complex UIs, building custom dialogs, implementing theme-aware background surfaces.",
        properties = listOf(
            "modifier: Modifier = Modifier",
            "shape: Shape = RectangleShape",
            "color: Color = MaterialTheme.colorScheme.surface",
            "content: @Composable () -> Unit"
        ),
        demo = { navController, name -> SurfaceLayout(navController, name) }
    )

    private fun createImage() = ComposableItem(
        name = "Image",
        description = "A flexible component for displaying bitmap or vector images with multiple scaling options. Supports content descriptions for accessibility and complex transformation effects.",
        category = CATEGORY_BASIC_COMP_LAYOUTS,
        commonUse = "User avatars in profile screens, product images in detail views, decorative illustrations in onboarding flows.",
        properties = listOf(
            "painter: Painter,",
            "contentDescription: String?,",
            "modifier: Modifier = Modifier,",
            "alignment: Alignment = Alignment.Center,",
            "contentScale: ContentScale = ContentScale.Fit,",
            "alpha: Float = DefaultAlpha,",
            "colorFilter: ColorFilter? = null"
        ),
        demo = { navController, name -> ImageComposable(navController, name) }
    )

    private fun createText() = ComposableItem(
        name = "Text",
        description = "The fundamental component for displaying read-only text in various typographic styles. Supports rich text formatting, inline emojis, and accessibility scaling.",
        category = CATEGORY_BASIC_COMP_LAYOUTS,
        commonUse = "Displaying article content, showing user-generated comments, rendering UI labels and headers across all app screens.",
        properties = listOf(
            "text: AnnotatedString,",
            "modifier: Modifier = Modifier,",
            "color: Color = Color.Unspecified,",
            "fontSize: TextUnit = TextUnit.Unspecified,",
            "fontStyle: FontStyle? = null,",
            "fontWeight: FontWeight? = null,",
            "fontFamily: FontFamily? = null,",
            "letterSpacing: TextUnit = TextUnit.Unspecified,",
            "lineHeight: TextUnit = TextUnit.Unspecified,",
            "textDecoration: TextDecoration? = null,",
            "textAlign: TextAlign? = null,"
        ),
        demo = { navController, name -> TextComposable(navController, name) }
    )

    private fun createScaffold() = ComposableItem(
        name = "Scaffold",
        description = "A top-level layout structure that implements Material Design app patterns. Provides slots for common screen elements like app bars, navigation rails, and floating action buttons.",
        category = CATEGORY_BASIC_COMP_LAYOUTS,
        commonUse = "Main app screens with persistent navigation elements, coordinating layout between multiple screen components, implementing Material 3 design system apps.",
        properties = listOf(
            "modifier: Modifier = Modifier,",
            "topBar: @Composable (() -> Unit) = {},",
            "bottomBar: @Composable (() -> Unit) = {},",
            "snackbarHost: @Composable (() -> Unit) = {},",
            "floatingActionButton: @Composable (() -> Unit) = {},",
            "floatingActionButtonPosition: FabPosition = FabPosition.End,",
            "containerColor: Color = MaterialTheme.colorScheme. background,"
        ),
        demo = { navController, name -> ScaffoldContainer(navController, name) }
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

    private fun createGraphisLayer() = ComposableItem(
        name = "GraphicsLayer",
        description = "A modifier that applies hardware-accelerated transformations and effects to composables. Enables complex visual manipulations without affecting layout measurements.",
        category = CATEGORY_ANIMATION,
        commonUse = "Creating 3D flip animations, implementing parallax scroll effects, building custom transition animations between states.",
        properties = listOf(
            "rotationZ: Float",
            "scaleX: Float",
            "scaleY: Float",
            "alpha: Float",
            "translationX: Float",
            "translationY: Float",
            "transformOrigin: TransformOrigin"
        ),
        demo = { navController, name -> GraphicsLayerEffect(navController, name) }
    )

    private fun createPager() = ComposableItem(
        name = "Pagers & Carousel",
        description = "Interactive scrollable containers implementing horizontal and vertical pagination patterns with swipe gestures.",
        category = CATEGORY_ANIMATION,
        commonUse = "For Onboarding screens, Product/image carousels, Full-screen gallery pagers, Tabbed interface navigation and Card stack presentations",
        properties = listOf(
            "state: PagerState,",
            "modifier: Modifier = Modifier,",
            "contentPadding: PaddingValues = PaddingValues(0.dp),",
            "pageSpacing: Dp = 0.dp,",
            "flingBehavior: TargetedFlingBehavior = PagerDefaults.flingBehavior(state = state),",
            "pageSize: PageSize = PageSize.Fill,"
        ),
        demo = { navController, name -> PagerAndCarouselAnim(navController, name) }
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

    private fun createComposePreview() = ComposableItem(
        name = "Compose Preview",
        description = "A development tool that enables real-time previewing of composable components without needing to deploy to a physical device or emulator.",
        category = CATEGORY_DEV_TOOLS,
        commonUse = "Rapid UI prototyping during development, visual testing of component states, team design reviews.",
        properties = listOf(
            "name: String? = null",
            "apiLevel: Int = -1",
            "showBackground: Boolean = false"
        ),
        demo = { navController, name -> ComposePreviewTool(navController, name) }
    )

    private fun createLayoutInspector() = ComposableItem(
        name = "Layout Inspector",
        description = "A debugging tool that visualizes the hierarchy and properties of composables in real-time. Integrates with Android Studio for detailed UI analysis and performance optimization.",
        category = CATEGORY_DEV_TOOLS,
        commonUse = "Diagnosing layout issues during development, analyzing rendering performance, understanding complex UI hierarchies.",
        properties = listOf(
            "liveMode: Boolean = false",
            "screenshotAnalysis: Boolean = true",
            "highlightBounds: Boolean = true"
        ),
        demo = { navController, name -> LayoutInspectorTool(navController, name) }
    )

}