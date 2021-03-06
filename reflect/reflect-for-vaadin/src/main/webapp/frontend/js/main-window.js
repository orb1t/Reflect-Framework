//This javascript implements the reflect responsive design rules (see JavaDoc of the ReflectResponsiveDesign class)

// ========== BIND_WINDOW EVENTS ==========
window.onload = function() {
	updateGui();
};
window.onresize = function() {
	updateGui();
};
// window.scroll=function() {updateGui()};

// ========== GLOBAL CONSTANTS ==========
var Size = {
	SMALL : 1,
	MEDIUM : 2,
	LARGE : 3,
};

var MAIN_MENU_WIDTH = 280;

// ========== GLOBALS ==========

var windowSize = {
	// See the javadoc of the ReflectDisplayWidth class
	horizontal : function() {
		if (window.innerWidth < 378) { // <10cm (phone size)
			return Size.SMALL;
		} else if (window.innerWidth < 756) { // <20cm (tablet size)
			return Size.MEDIUM;
		} else { // >20cm (desktop size)
			return Size.LARGE;
		}
	},
	vertical : function() {
		// See the javadoc of the ReflectDisplayHeight class
		if (window.innerHeight < 378) { // <10cm (phone size)
			return Size.SMALL;
		} else if (window.innerHeight < 756) { // <20cm (tablet size)
			return Size.MEDIUM;
		} else { // >20cm (desktop size)
			return Size.LARGE;
		}
	}
}

var mainMenuOpen = true;

// ========== MAIN MENU FUNCTIONS ==========
function openMainMenu() {
	mainMenuOpen = true;
	updateGui();
}

function closeMainMenu() {
	mainMenuOpen = false;
	updateGui();
}

function toggleMainMenu() {
	mainMenuOpen = !mainMenuOpen;
	updateGui();
}

// ========== UPDATE GUI FUNCTIONS ==========

function updateGui() {
	var windowSizeHorizontal = windowSize.horizontal();
	var windowSizeVertical = windowSize.vertical();

	updateHeader(windowSizeHorizontal, windowSizeVertical);
	updateMainMenu(windowSizeHorizontal, windowSizeVertical);
	updateContent(windowSizeHorizontal, windowSizeVertical);
	updateContentOverlay(windowSizeHorizontal, windowSizeVertical);
}

function updateHeader(windowSizeHorizontal, windowSizeVertical) {
	header = document.getElementById("headerBar")
	if (header) {
		header.style.position = "fixed";
		header.style.top = "0px";
		header.style.left = "0px";
		updateTitle(windowSizeHorizontal);
		updateTabHeaders(windowSizeHorizontal);
	}
}

function updateTitle(windowSizeHorizontal) {
	title = document.getElementById("title");
	if (title) {
		if (mainMenuOpen || windowSizeHorizontal >= Size.LARGE) {
			title.style.display = "block";
			title.style.width = MAIN_MENU_WIDTH - title.offsetLeft + "px";
		} else {
			title.style.display = "none";
		}
	}
}

function updateTabHeaders(windowSizeHorizontal) {
	tabHeaders = document.getElementById("tabHeaderBar");
	title = document.getElementById("title");
	contextMenuButton = document.getElementById("contextMenuButton");
	if (tabHeaders && title && contextMenuButton) {
		// TODO tabHeaders resizing seems to be overridden somehow: with small
		// window/viewport it is not minimized and steals width from title
		if (mainMenuOpen || windowSizeHorizontal >= Size.LARGE) {
			tabHeaders.style.width = (window.offsetWidth - MAIN_MENU_WIDTH - contextMenuButton.offsetLeft)
					+ "px";// TODO contextMenuButton may not always be visible
		} else {
			tabHeaders.style.width = (window.offsetWidth - title.offsetLeft - contextMenuButton.offsetLeft)
					+ "px";// TODO contextMenuButton may not always be visible
		}
	}
}

function updateContent(windowSizeHorizontal, windowSizeVertical) {
	content = document.getElementById("tabContentPanel");
	if (content) {
		// content fixed under header, with the remaining height
		content.style.alignSelf = null;
		content.style.position = "fixed";
		if (mainMenuOpen && windowSizeHorizontal >= Size.LARGE) {
			// content next to main menu on the left
			content.style.left = MAIN_MENU_WIDTH + "px";
			content.style.width = (window.innerWidth - MAIN_MENU_WIDTH) + "px";
		} else {
			// content has full width
			content.style.left = "0px";
			content.style.width = (window.innerWidth) + "px";
		}
		content.style.height = (window.innerHeight - header.offsetHeight)
				+ "px";
		content.style.top = header.offsetHeight + "px";

		makeChildrenSameSize(content);
	}
}

function updateMainMenu(windowSizeHorizontal, windowSizeVertical) {
	header = document.getElementById("headerBar")
	mainMenu = document.getElementById("mainMenu");
	if (header && mainMenu) {
		mainMenu.style.alignSelf = null;
		mainMenu.style.position = "fixed";
		if (mainMenuOpen) {
			// show main menu on the left
			mainMenu.style.left = "0px";
		} else {
			// main menu left from viewport
			mainMenu.style.left = -MAIN_MENU_WIDTH + "px";
		}
		// content fixed under header, with the remaining height
		mainMenu.style.top = header.offsetHeight + "px";
		mainMenu.style.width = MAIN_MENU_WIDTH + "px";
		mainMenu.style.height = (window.innerHeight - header.offsetHeight)
				+ "px";
		makeChildrenSameSize(mainMenu);
	}
}

function updateContentOverlay(windowSizeHorizontal, windowSizeVertical) {
	contentOverlay = document.getElementById("overlay");
	if (contentOverlay) {
		if (mainMenuOpen && windowSizeHorizontal < Size.LARGE) {
			contentOverlay.style.display = "block";
			contentOverlay.style.top = "0px";
			contentOverlay.style.left = MAIN_MENU_WIDTH + "px";
			contentOverlay.style.width = (window.innerWidth - MAIN_MENU_WIDTH)
					+ "px";
			contentOverlay.style.height = window.innerHeight + "px";
		} else {
			contentOverlay.style.display = "none";
		}
	}
}

function makeChildrenSameSize(parent) {
	var children = parent.children;
	var jQueryResult = $(parent);
	var parentOuterHeight = jQueryResult.outerHeight(false);
	for (i = 0; i < children.length; i++) {
		var child = children[i];
		jQueryResult = $(child);

		var childOuterHeight = jQueryResult.outerHeight(false);
		var childHeight = jQueryResult.height();
		// childExtraHight = borders + padding + scroll bars
		var childExtraHight = childOuterHeight - childHeight;
		child.style.height = parentOuterHeight - childExtraHight + "px";

		// setting the calculated (parent) width (see height calculation above)
		// breaks scrolling!!!
		child.style.width = "";
	}
}

// TODO header to scroll out of viewport when windowHeight<Medium and otherwise
// stay fixed
// TODO title is crushed when menu is open window gets small: tabs seem to have
// a mimimum width...

