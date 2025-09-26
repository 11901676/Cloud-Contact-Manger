console.log("contact.js");

const viewContactModal = document.getElementById("view_contact_modal");


const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal()
{
    contactModal.show();
}

function closeContactModal()
{
    contactModal.hide();
}

// async function loadContactData(id)
// {
//     console.log(id);
//     try {
//         const data = await(await fetch(`http://localhost:8081/api/contacts/${id}`)).json();
//         console.log(data);
//         document.getElementById("contact_name").innerHTML = data.name;
//         openContactModal();
//     } catch (error) {
//         console.log("Error: ", error);
//     }
// }

async function loadContactData(id) {
    console.log("Fetching data for contact ID:", id);
    const url = `http://localhost:8081/api/contacts/${id}`; // Ensure this URL is correct

    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        console.log("Data received:", data);

        // --- Populate the modal with the fetched data ---

        // Update Text Content (using || '' to ensure no 'null' strings are displayed)
        document.getElementById('contact_name').textContent = data.name || 'N/A';
        document.getElementById('contact_phone').textContent = data.phoneNumber || 'N/A';
        document.getElementById('contact_address').textContent = data.address || 'N/A';
        document.getElementById('contact_description').textContent = data.description || 'No description provided.';
        
        // Update Email Link
        const emailLink = document.getElementById('contact_email');
        emailLink.textContent = data.email || 'N/A';
        emailLink.href = data.email ? `mailto:${data.email}` : '#';

        // Update Profile Picture
        const imageElement = document.getElementById('contact_image');
        imageElement.src = data.picture || '/images/default_profile.png'; // Fallback to a default image

        // Update Social Links (and hide them if not present)
        const linkedinLink = document.getElementById('contact_linkedin');
        const websiteLink = document.getElementById('contact_website');

        if (data.linkedInLink) {
            linkedinLink.href = data.linkedInLink;
            linkedinLink.classList.remove('hidden'); // Show if data exists
        } else {
            linkedinLink.classList.add('hidden'); // Hide if no data
        }

        if (data.websiteLink) {
            websiteLink.href = data.websiteLink;
            websiteLink.classList.remove('hidden'); // Show if data exists
        } else {
            websiteLink.classList.add('hidden'); // Hide if no data
        }
        
        // Finally, show the modal after all data is loaded
        contactModal.show();

    } catch (error) {
        console.error("Error fetching contact data:", error);
        // Optionally, show an error message in the modal or to the user
    }
}