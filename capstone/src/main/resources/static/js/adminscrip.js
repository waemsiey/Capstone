document.addEventListener('DOMContentLoaded', function () {
  var sideToggle = document.getElementById('sidebarToggle'); // Sidebar toggle button
  var navPills = document.querySelector('.nav-pills'); // Navigation pills element
  var content = document.querySelector('.content'); // Main content area
  var sidebarIcon = document.getElementById('sidebarIcon'); // Sidebar icon element
  var productsButton = document.getElementById('v-pills-products-tab'); // Products tab button
  var productIcon = document.getElementById('collapseMenu'); // Product collapse icon
  var productsCollapse = document.getElementById('v-products-collapse'); // Product collapse content
  var dropdownItems = document.querySelectorAll('.dropdown-item'); // Dropdown items for the navbar
  var dropdownToggle = document.querySelector('.dropdown-toggle'); // Dropdown toggle button

  // Initialize UI settings on page load
  initUI();

  // Set up the calendar for the current month
  generateCalendar(currentMonth, currentYear); 

  // Event listener for sidebar toggle button
  sideToggle.addEventListener('click', toggleSidebar);
  
  // Event listener for product toggle icon
  productsButton.addEventListener('click', toggleProductCollapse);

  // Select all products/inventory checkboxes
  setupSelectAllCheckboxes();

  // Setup AJAX form submission for adding products
  setupFormSubmission();

  // Set up event handler for adding dynamic input options
  const addBtn = document.querySelector(".add");
  if (addBtn) {
      addBtn.addEventListener("click", addInput); // Click event for adding options
  } else {
      console.error("Add button not found.");
  }
});

// Initialize UI components
function initUI() {
  var navPills = document.querySelector('.nav-pills');
  var content = document.querySelector('.content');
  var sidebarIcon = document.getElementById('sidebarIcon');

  navPills.classList.add('collapsed'); // Collapse navigation pills on load
  content.classList.add('collapsed'); // Collapse main content area on load
  sidebarIcon.classList.remove('bx-x'); // Change icon to menu
  sidebarIcon.classList.add('bx-menu');
}

// Toggle sidebar visibility
function toggleSidebar() {
  var navPills = document.querySelector('.nav-pills');
  var content = document.querySelector('.content');
  var sidebarIcon = document.getElementById('sidebarIcon');

  navPills.classList.toggle('collapsed'); // Toggle collapsed class
  content.classList.toggle('collapsed'); // Toggle collapsed class

  // Change sidebar icon based on visibility
  sidebarIcon.classList.toggle('bx-x', !navPills.classList.contains('collapsed'));
  sidebarIcon.classList.toggle('bx-menu', navPills.classList.contains('collapsed'));
}

// Toggle product collapse menu
function toggleProductCollapse() {
  var productIcon = document.getElementById('collapseMenu');
  var productsCollapse = document.getElementById('v-products-collapse');

  // Toggle collapse state and icon
  productIcon.classList.toggle('bx-chevron-up', productsCollapse.classList.toggle('show'));
  productIcon.classList.toggle('bx-chevron-down', !productsCollapse.classList.contains('show'));
}

// Setup calendar variables
let currentMonth = new Date().getMonth(); // Current month (0-11)
let currentYear = new Date().getFullYear(); // Current year

// Generate and display calendar for the current month
function generateCalendar(month, year) {
    const calendar_days = document.querySelector('.calendar-days');
    const month_names = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

    // Update month and year display
    document.querySelector('#month-picker').textContent = month_names[month]; // Set month name
    document.querySelector('#year').textContent = year; // Set current year

    // Clear previous days
    calendar_days.innerHTML = '';

    const days_of_month = [31, getFebDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    const first_day = new Date(year, month, 1);

    // Generate days of the month
    for (let i = 0; i < days_of_month[month] + first_day.getDay(); i++) {
        const day = document.createElement('div'); // Create a new day element
        if (i >= first_day.getDay()) {
            const dayNumber = i - first_day.getDay() + 1; // Calculate the day number
            day.textContent = dayNumber; // Set day number as text
            day.classList.add('calendar-day');
            day.dataset.date = `${year}-${month + 1}-${dayNumber}`; // Set data attribute for date

            // Highlight today's date
            if (dayNumber === new Date().getDate() && year === new Date().getFullYear() && month === new Date().getMonth()) {
                day.classList.add('current-date');
            }

            // Click event for selecting a date
            day.addEventListener('click', function () {
                selectDate(this); // Select the clicked day
            });
        }
        calendar_days.appendChild(day); // Append day to calendar
    }
}

// Functions to handle year and month changes
document.getElementById('pre-year').addEventListener('click', function () {
    currentYear--; // Decrement year
    generateCalendar(currentMonth, currentYear); // Regenerate calendar
});

document.getElementById('next-year').addEventListener('click', function () {
    currentYear++; // Increment year
    generateCalendar(currentMonth, currentYear); // Regenerate calendar
});

document.getElementById('pre-month').addEventListener('click', function () {
    if (currentMonth === 0) { // If January, go to December of previous year
        currentMonth = 11;
        currentYear--;
    } else {
        currentMonth--; // Decrement month
    }
    generateCalendar(currentMonth, currentYear); // Regenerate calendar
});

document.getElementById('next-month').addEventListener('click', function () {
    if (currentMonth === 11) { // If December, go to January of next year
        currentMonth = 0;
        currentYear++;
    } else {
        currentMonth++; // Increment month
    }
    generateCalendar(currentMonth, currentYear); // Regenerate calendar
});

// Initial call to generate the current month's calendar
generateCalendar(currentMonth, currentYear);

// Helper function to determine the number of days in February
function getFebDays(year) {
    return (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0) ? 29 : 28; // Return days in February
}

// Select date and highlight
function selectDate(selectedDay) {
  // Deselect previous selection
  document.querySelectorAll('.calendar-day.selected').forEach(day => {
    day.classList.remove('selected');
  });

  // Select the clicked day
  selectedDay.classList.add('selected');

  // Optionally display selected date somewhere
  const selectedDateDisplay = document.getElementById('selected-date');
  selectedDateDisplay.textContent = `Selected Date: ${selectedDay.dataset.date}`; // Show selected date
}

// Setup "Select All" checkboxes
function setupSelectAllCheckboxes() {
  document.getElementById('selectAllProducts').addEventListener('change', function() {
      toggleCheckboxes('input[name="productCheck"]', this.checked); // Toggle all product checkboxes
  });
  document.getElementById('selectAllInventory').addEventListener('change', function() {
      toggleCheckboxes('input[name="inventoryCheck"]', this.checked); // Toggle all inventory checkboxes
  });
}

// Function to toggle the state of checkboxes based on 'Select All'
function toggleCheckboxes(selector, isChecked) {
  document.querySelectorAll(selector).forEach(checkbox => {
      checkbox.checked = isChecked; // Set checkbox state
  });
}

// Setup form submission with AJAX
function setupFormSubmission() {
  $('#addProductForm').on('submit', function(event) {
      event.preventDefault(); // Prevent default form submission

      var formData = new FormData(this); // Gather form data
      $.ajax({
          url: $(this).attr('action'), // Get form action URL
          type: 'POST',
          data: formData,
          processData: false,
          contentType: false,
          success: function(response) {
              showMessage('alert-success', response.message); // Show success message
              window.location.href = '/admin/products'; // Redirect after successful submission
          },
          error: function(xhr) {
              var errorMessage = xhr.responseJSON ? xhr.responseJSON.message : 'An error occurred.'; // Get error message
              showMessage('alert-danger', errorMessage); // Show error message
          }
      });
  });
}

// Show messages to the user
function showMessage(alertClass, message) {
  $('#message').removeClass('alert-danger alert-success').addClass(alertClass).text(message).show(); // Update message display
}
// For Variant Values Section where adding new variant field places
const columnNames = ["Size", "Color", "Printing Type"];
const addBtn = document.querySelector(".add");
const inputContainer = document.querySelector(".variant-group");
const displayedOptions = document.querySelector(".display-options");

// Function to remove the input group with a confirmation prompt
function removeInput() {
    if (confirm("Are you sure you want to delete this option group?")) {
        this.closest(".flex").remove();
    }
}

// Function to complete input and move it to the display section
function completeInput() {
    const optionGroup = this.closest(".flex").querySelector(".label-input-container");
    const optionName = optionGroup.querySelector("select.option-select").value.trim();

    // Collect all option values, including dynamically added ones
    const optionValues = Array.from(optionGroup.querySelectorAll("input[type='text']"))
        .map(input => input.value.trim())
        .filter(value => value);

    // Validation
    if (!optionName) {
        alert("Please select a valid option name.");
        return;
    }
    if (optionValues.length === 0) {
        alert("Please provide at least one option value.");
        return;
    }

    let optionDisplay = displayedOptions.querySelector(`.option-display[data-name="${optionName}"]`);

    if (optionDisplay) {
        // If option display exists, update values
        const valuesElement = optionDisplay.querySelector(".value-element-container");
        valuesElement.innerHTML = ''; // Clear current values

        optionValues.forEach(value => {
            const valueBox = document.createElement("div");
            valueBox.className = "option-value-box";
            valueBox.textContent = value;
            valuesElement.appendChild(valueBox);
        });
    } else {
        // If option display does not exist, create a new one
        const newDisplay = createOptionDisplay(optionName, optionValues);
        displayedOptions.appendChild(newDisplay);
    }

    this.closest(".flex").remove();
}

// Function to create the displayed option name and values with edit/delete buttons
function createOptionDisplay(optionName, optionValues) {
    const optionDisplay = document.createElement("div");
    optionDisplay.className = "option-display";
    optionDisplay.dataset.name = optionName;

    const nameElement = document.createElement("div");
    nameElement.className = "option-name";
    nameElement.textContent = optionName;

    // Create a container for the value boxes
    const valuesContainer = document.createElement("div");
    valuesContainer.className = "value-element-container";

    // Create a separate box for each option value
    optionValues.forEach(value => {
        const valueBox = document.createElement("div");
        valueBox.className = "option-value-box"; // Apply your existing styling
        valueBox.textContent = value; // Set the text to the value
        valuesContainer.appendChild(valueBox); // Add the box to the container
    });

    // Append the name and values container to the option display
    optionDisplay.appendChild(nameElement);
    optionDisplay.appendChild(valuesContainer);

    // Create Edit button
    const editBtn = document.createElement("button");
    editBtn.textContent = "Edit";
    editBtn.className = "edit-button";
    editBtn.addEventListener("click", function () {
        const inputFields = addInput(optionName, optionValues);
        optionDisplay.replaceWith(inputFields);
    });

    // Create Delete button
    const removeBtn = document.createElement("a");
    removeBtn.href = "#";
    removeBtn.innerHTML = "&times;";
    removeBtn.className = "delete";
    removeBtn.addEventListener("click", function () {
        if (confirm("Are you sure you want to delete this option group?")) {
            optionDisplay.remove();
        }
    });

    // Create a container for the buttons
    const btnConEdit = document.createElement("div");
    btnConEdit.className = "button-container";
    btnConEdit.appendChild(editBtn);
    btnConEdit.appendChild(removeBtn);

    // Append the button container to the option display
    optionDisplay.appendChild(btnConEdit);

    return optionDisplay;
}

// Function to create input fields for new options or editing existing ones
function addInput(existingName = "", existingValues = []) {
    const valueLabel = document.createElement("label");
    valueLabel.textContent = "Option Values";

    // Create a select element for allowed options
    const optionSelect = document.createElement("select");
    optionSelect.className = "option-select";

    // Populate select with allowed options
    columnNames.forEach(column => {
        const option = document.createElement("option");
        option.value = column;
        option.textContent = column;
        optionSelect.appendChild(option);
    });

    // Set the existing name as the selected option if it matches
    if (existingName && columnNames.includes(existingName)) {
        optionSelect.value = existingName;
    }

    // Create the label input container and append value label and select
    const labelInputContainer = document.createElement("div");
    labelInputContainer.className = "label-input-container";
    labelInputContainer.appendChild(valueLabel);
    labelInputContainer.appendChild(optionSelect);

    // Function to add new input fields for adding option values
    function addNewValueInput(value = "") {
        const newOptionValue = document.createElement("input");
        newOptionValue.type = "text";
        newOptionValue.placeholder = `(e.g. '${optionSelect.value === "Size" ? "Large" : optionSelect.value === "Color" ? "Red" : "Screen Printing"}')`;
        newOptionValue.value = value;
        labelInputContainer.appendChild(newOptionValue);
    }

    // Populate existing values into input fields
    existingValues.forEach(value => addNewValueInput(value));
    addNewValueInput(); // Add an empty input for new values

    const addValueBtn = document.createElement("a");
    addValueBtn.innerHTML = "&plus;";
    addValueBtn.addEventListener("click", () => addNewValueInput());

    const doneBtn = document.createElement("button");
    doneBtn.className = "done";
    doneBtn.textContent = "Done";
    doneBtn.type = "button";
    doneBtn.addEventListener("click", completeInput);

    const deleteBtn = document.createElement("a");
    deleteBtn.className = "delete";
    deleteBtn.innerHTML = "&times;";
    deleteBtn.addEventListener("click", removeInput);

    const buttonContainer = document.createElement("div");
    buttonContainer.className = "button-container";
    buttonContainer.appendChild(deleteBtn);
    buttonContainer.appendChild(doneBtn);
    buttonContainer.appendChild(addValueBtn);

    const flex = document.createElement("div");
    flex.className = "flex";
    flex.appendChild(labelInputContainer);
    flex.appendChild(buttonContainer);

    inputContainer.appendChild(flex);

    // Add event listener to update the placeholder on option change
    optionSelect.addEventListener("change", function () {
        const inputs = labelInputContainer.querySelectorAll("input[type='text']");
        const placeholderText = `(e.g. '${optionSelect.value === "Size" ? "Large" : optionSelect.value === "Color" ? "Red" : "Screen Printing"}')`;
        inputs.forEach(input => input.placeholder = placeholderText);
    });

    return flex;
}

// Add event listener for "Add Option Group" button
addBtn.addEventListener("click", function (e) {
    e.preventDefault();
    inputContainer.appendChild(addInput());
});
