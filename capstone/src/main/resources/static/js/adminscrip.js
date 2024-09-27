document.addEventListener('DOMContentLoaded', function () {
    var sideToggle = document.getElementById('sidebarToggle'); //side
    var navPills = document.querySelector('.nav-pills');
    var content = document.querySelector('.content');
    var sidebarIcon = document.getElementById('sidebarIcon');
    var productsButton = document.getElementById('v-pills-products-tab'); //collapse menu
    var productIcon = document.getElementById('collapseMenu');
    var productsCollapse = document.getElementById('v-products-collapse');
    var dropdownItems = document.querySelectorAll('.dropdown-item'); //table selections
    var dropdownToggle = document.querySelector('.dropdown-toggle');
    
    // Hide the slide menu
    navPills.classList.add('collapsed');
    content.classList.add('collapsed');
    sidebarIcon.classList.remove('bx-x');
    sidebarIcon.classList.add('bx-menu');
    

    // Event listener for sidebar toggle button
    sideToggle.addEventListener('click', function () {
        navPills.classList.toggle('collapsed');
        content.classList.toggle('collapsed');

        if (navPills.classList.contains('collapsed')) {
            sidebarIcon.classList.remove('bx-x');
            sidebarIcon.classList.add('bx-menu');
        } else {
            sidebarIcon.classList.remove('bx-menu');
            sidebarIcon.classList.add('bx-x');
        }
    });
    
    // Event listnerfor product tggleicon
    productsButton.addEventListener('click', function(){
        
        if(productsCollapse.classList.contains('show')){
            productIcon.classList.remove('bx-chevron-up');
            productIcon.classList.add('bx-chevron-down');
        }else{
            productIcon.classList.remove('bx-chevron-down');
            productIcon.classList.add('bx-chevron-up');
        }
    });

    // Calendar generation code
    let calendar_days = document.querySelector('.calendar-days');
    calendar_days.innerHTML = '';
    let calendar_header_year = document.querySelector('#year'); 
    let month_picker = document.querySelector('#month-picker'); 
    let days_of_month = [
        31, getFebDays(currentYear.value), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    ];

    let currentDate = new Date();
    let month_names = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

    month_picker.innerHTML = month_names[currentMonth.value];
    calendar_header_year.innerHTML = currentYear.value;

    let first_day = new Date(currentYear.value, currentMonth.value);

    for(let i = 0; i <= days_of_month[currentMonth.value] + first_day.getDay() - 1; i++){
        let day = document.createElement('div');
        if(i >= first_day.getDay()){
            day.innerHTML = i - first_day.getDay() + 1;
            if(i - first_day.getDay() + 1 === currentDate.getDate() && currentYear.value === currentDate.getFullYear() && currentMonth.value === currentDate.getMonth()){
                day.classList.add('current-date');
            }
        }
        calendar_days.appendChild(day);
    }

    // Month list toggle
    let month_list = document.querySelector('.month_list');
    month_names.forEach((e, index) => {
        let month = document.createElement('div');
        month.innerHTML = `<div>${e}</div>`;
        month_list.append(month);
        month.onclick = () => {
            currentMonth.value = index;
            generateCalendar(currentMonth.value, currentYear.value);
            month_list.classList.replace('show', 'hide'); // Fixed class replacement
            dayTextFormate.classList.remove('hideTime');
            dayTextFormate.classList.add('showtime');
            timeFormate.classList.remove('hideTime');
            timeFormate.classList.add('showtime');
            dateFormate.classList.remove('hideTime');
            dateFormate.classList.add('showtime');
        };
    });

    // Hide month list initially
    (function () {
        month_list.classList.add('hideonce');
    })();

    // Year navigation
    document.querySelector('#pre-year').onclick = () => {
        --currentYear.value;
        generateCalendar(currentMonth.value, currentYear.value);
    };
    document.querySelector('#next-year').onclick = () => {
        ++currentYear.value;
        generateCalendar(currentMonth.value, currentYear.value);
    };

    // Initial calendar generation
    let currentMonth = {value: currentDate.getMonth()};
    let currentYear = {value: currentDate.getFullYear()};
    generateCalendar(currentMonth.value, currentYear.value);

    // Display current date and time
    const todayShowTime = document.querySelector('.time-formate');
    const todayShowDate = document.querySelector('.date-formate');

    const currshowDate = new Date();
    const showCurrentOption = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        weekday: 'long',
    };

    todayShowDate.innerHTML = currshowDate.toLocaleDateString(undefined, showCurrentOption);

});

// Helper function to calculate leap year days in February
function getFebDays(year) {
    return (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0) ? 29 : 28;
}

// Calendar generation function (assumed to be present)
function generateCalendar(month, year) {
    // Code to regenerate the calendar for the specified month and year
}
