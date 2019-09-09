window.addEventListener("load", initialize);
function initialize() {
	handleSidebar();
}
function handleSidebar() {
	document.getElementById("sidebar").addEventListener("click",toggleSidebar);
}
function toggleSidebar() {
	document.getElementById("sidebar").classList.toggle('active');
}