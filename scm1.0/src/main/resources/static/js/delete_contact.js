console.log("contactDelete.js loaded");

// 1. Initialize the modal
const deleteModalElement = document.getElementById('delete_contact_modal');
const deleteModal = new Modal(deleteModalElement);

// This is the function that is called by the 'onclick' in your HTML
function deleteContact(id) {
    console.log("Contact ID to delete:", id);

    // 2. Find the confirmation button inside the modal
    const confirmButton = document.getElementById('delete-confirm-button');

    // 3. Set its 'href' attribute to the correct delete URL
    confirmButton.href = `/user/contacts/delete/${id}`;

    // 4. Show the modal
    deleteModal.show();
}