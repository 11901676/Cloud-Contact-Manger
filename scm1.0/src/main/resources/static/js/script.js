console.log("script.js loaded");

let currentTheme = getTheme() || 'light'; // default to light theme if not set

//set theme to local storage
function setTheme(theme) {
    localStorage.setItem('theme', theme);
}

//get theme from local storage
function getTheme() {
    return localStorage.getItem('theme');
}

//theme will get changed after the DOM is ready or the content is fully loaded
document.addEventListener("DOMContentLoaded", function() {
    changeTheme();
});

function changeTheme(){
    //add the current theme to the html element
    document.querySelector("html").classList.add(currentTheme);

    //toggle the theme between light and dark
    
    document.getElementById("theme_change_button").addEventListener("click", function() {
        let oldTheme = currentTheme;

        console.log("theme_change_button clicked");
        console.log(currentTheme);
        if (currentTheme === 'light') {
            currentTheme = 'dark';
        } else {
            currentTheme = 'light';
        }
        //set the theme to local storage
        setTheme(currentTheme);

        //remove the old theme from the html element
        document.querySelector("html").classList.remove(oldTheme);

        //add the new theme to the html element
        document.querySelector("html").classList.add(currentTheme);

        //change the button text as well
        document.getElementById("theme_change_button").querySelector("span").textContent = currentTheme === 'light' ? "Dark" : "Light";
    }
    );
}


//toggle theme between light and dark
function toggleTheme() {
    let theme = localStorage.getItem('theme');
    return theme ? theme : "light";
}