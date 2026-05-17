const BASE_URL = "http://localhost:8080";

function signup() {

    const user = {
        name:
        document.getElementById("name").value,

        email:
        document.getElementById("email").value,

        password:
        document.getElementById("password").value
    };

    fetch(BASE_URL + "/auth/signup", {

        method: "POST",

        headers: {
            "Content-Type":
            "application/json"
        },

        body: JSON.stringify(user)

    })

    .then(response => response.text())

    .then(data => {

        alert(data);

        window.location.href =
        "login.html";
    });
}

function login() {

    const user = {

        email:
        document.getElementById("loginEmail").value,

        password:
        document.getElementById("loginPassword").value
    };

    fetch(BASE_URL + "/auth/login", {

        method: "POST",

        headers: {
            "Content-Type":
            "application/json"
        },

        body: JSON.stringify(user)

    })

    .then(response => response.text())

    .then(data => {

        alert(data);

        if(data === "Login Successful") {

            window.location.href =
            "dashboard.html";
        }
    });
}
function createNote() {

    const note = {

        title:
        document.getElementById("title").value,

        content:
        document.getElementById("content").value,

        tags:
        document.getElementById("tags").value,

        category:
        document.getElementById("category").value
    };

    fetch(BASE_URL + "/notes", {

        method: "POST",

        headers: {
            "Content-Type":
            "application/json"
        },

        body: JSON.stringify(note)

    })

	.then(data => {

	    alert(data);

	    document.getElementById(
	    "title").value = "";

	    document.getElementById(
	    "content").value = "";

	    document.getElementById(
	    "tags").value = "";

	    document.getElementById(
	    "category").value = "";

	    loadNotes();
	});
}

function loadNotes() {

    fetch(BASE_URL + "/notes")

    .then(response => response.json())

    .then(notes => {

        let output = "";
		
		document.getElementById(
		"totalNotes").innerText =
		notes.length;

        notes.forEach(note => {

            output += `
            <div class="note-card">

            <h3>${note.title}</h3>

            <p>${note.content}</p>

            <p>${note.tags}</p>

            <button onclick="deleteNote(${note.id})">
            Delete
            </button>

            <button onclick="generateSummary(${note.id})">
            AI Summary
            </button>

            </div>
            `;
        });

        document.getElementById(
        "notesContainer").innerHTML
        = output;
    });
}

function deleteNote(id) {

    fetch(BASE_URL + "/notes/" + id, {

        method:"DELETE"
    })

    .then(response => response.text())

    .then(data => {

        alert(data);

        loadNotes();
    });
}

function generateSummary(id) {

    fetch(BASE_URL +
    "/notes/" + id +
    "/generate-summary", {

        method:"POST"
    })

    .then(response => response.text())

    .then(data => {

        alert(data);
    });
}

function searchNotes() {

    let input =
    document.getElementById(
    "searchInput")
    .value.toLowerCase();

    let cards =
    document.getElementsByClassName(
    "note-card");

    for(let i=0;
        i<cards.length;
        i++){

        let text =
        cards[i]
        .innerText
        .toLowerCase();

        cards[i].style.display =
        text.includes(input)
        ? "block"
        : "none";
    }
}
window.onload = loadNotes;