async function loadProjects() {

    const response = await fetch("/api/projects");

    if (!response.ok) {
        alert("Không thể tải danh sách project!");
        return;
    }

    const projects = await response.json();

    const container = document.getElementById("projects");

    container.innerHTML = "";

    projects.forEach(project => {

        container.innerHTML += `

            <div class="project-card">

                <h3>${project.title}</h3>

                <p>${project.description}</p>

                <p>ID: ${project.id}</p>

                <a href="${project.githubUrl || "#"}"
                   target="_blank">
                    GitHub
                </a>

                <br>

                <a href="${project.demoUrl || "#"}"
                   target="_blank">
                    Demo
                </a>

                <div class="project-actions">

                    <button onclick="editProject(${project.id})">
                        Sửa
                    </button>

                    <button onclick="deleteProject(${project.id})">
                        Xóa
                    </button>

                </div>

            </div>
        `;
    });
}
async function saveProject() {

    const id = document.getElementById("projectId").value;

    const project = {

        title: document.getElementById("title").value,

        description: document.getElementById("description").value,

        githubUrl: document.getElementById("githubUrl").value,

        demoUrl: document.getElementById("demoUrl").value,

        imageUrl: document.getElementById("imageUrl").value
    };


    let response;


    // Không có ID → THÊM
    if (id === "") {

        response = await fetch("/api/projects", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(project)
        });

    }

    // Có ID → SỬA
    else {

        response = await fetch(`/api/projects/${id}`, {

            method: "PUT",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(project)
        });
    }


    if (!response.ok) {

        alert("Thao tác thất bại!");

        return;
    }


    alert(
        id === ""
            ? "Thêm project thành công!"
            : "Sửa project thành công!"
    );


    clearForm();

    loadProjects();
}
async function editProject(id) {

    const response = await fetch(`/api/projects/${id}`);

    if (!response.ok) {

        alert("Không tìm thấy project!");

        return;
    }


    const project = await response.json();


    document.getElementById("projectId").value = project.id;

    document.getElementById("title").value =
        project.title;

    document.getElementById("description").value =
        project.description;

    document.getElementById("githubUrl").value =
        project.githubUrl || "";

    document.getElementById("demoUrl").value =
        project.demoUrl || "";

    document.getElementById("imageUrl").value =
        project.imageUrl || "";


    document.getElementById("formTitle").innerText =
        "Sửa Project";
}
async function deleteProject(id) {

    const confirmDelete =
        confirm("Bạn có chắc muốn xóa project này không?");


    if (!confirmDelete) {
        return;
    }


    const response = await fetch(
        `/api/projects/${id}`,
        {
            method: "DELETE"
        }
    );


    if (!response.ok) {

        alert("Xóa project thất bại!");

        return;
    }


    alert("Xóa project thành công!");


    loadProjects();
}
function cancelEdit() {

    clearForm();
}
function clearForm() {

    document.getElementById("projectId").value = "";

    document.getElementById("title").value = "";

    document.getElementById("description").value = "";

    document.getElementById("githubUrl").value = "";

    document.getElementById("demoUrl").value = "";

    document.getElementById("imageUrl").value = "";

    document.getElementById("formTitle").innerText =
        "Thêm Project";
}
loadProjects();