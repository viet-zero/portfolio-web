async function loadProjects() {

    const response = await fetch("/api/projects");

    const projects = await response.json();

    const container = document.getElementById("projects");

    container.innerHTML = "";

    projects.forEach(project => {

        const div = document.createElement("div");

        div.innerHTML = `
            <h2>${project.title}</h2>

            <p>${project.description}</p>

            <p>ID: ${project.id}</p>

            <a href="${project.githubUrl}" target="_blank">
                GitHub
            </a>

            <br>

            <button onclick="editProject(${project.id})">
                sửa
            </button>

            <button onclick="deleteProject(${project.id})">
                Xóa
            </button>

            <hr>
        `;

        container.appendChild(div);
    });
}


function showAddForm() {

    document.getElementById("addForm").style.display = "block";

}


async function addProject() {

    const project = {

        title: document.getElementById("title").value,

        description: document.getElementById("description").value,

        githubUrl: document.getElementById("githubUrl").value,

        demoUrl: document.getElementById("demoUrl").value,

        imageUrl: document.getElementById("imageUrl").value

    };

    await fetch("/api/projects", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(project)

    });

    loadProjects();
}


async function deleteProject(id) {

    await fetch(`/api/projects/${id}`, {

        method: "DELETE"

    });

    loadProjects();
}
async function editProject(id){
    const response = await fetch(`/api/projects/${id}`);
    if(!response.ok){
        alert("Không tìm thấy dự án với ID:"+id);
        return;
    }
    const project = await response.json();
     //lấy data cũ
    const title = prompt("Tên project:", project.title);
    const description = prompt("Mô tả:", project.description);
    const githubUrl = prompt("GitHub URL:", project.githubUrl || "");
    const demoUrl = prompt("Demo URL:", project.demoUrl || "");
    const imageUrl = prompt("Image URL:", project.imageUrl || "");

    if (title === null || description === null) {
        return;
    }
    const updatedProject = {
        title: title,
        description: description,
        githubUrl: githubUrl,
        demoUrl: demoUrl,
        imageUrl: imageUrl
    };

    const updateResponse = await fetch(`/api/projects/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(updatedProject)
    });

    if (!updateResponse.ok) {
        alert("Sửa project thất bại!");
        return;
    }

    alert("Sửa project thành công!");

    loadProjects();
}
        


loadProjects();