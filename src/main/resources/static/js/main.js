const userFetch = {
    allUsers : async () => await fetch("http://localhost:8080/rest/admin"),
    oneUSer : async (id) => await fetch ("http://localhost:8080/rest/admin/" + id),
    singleUser : async () => await fetch("http://localhost:8080/rest/admin/user")
}

userFetch.singleUser()
    .then(response => response.json())
    .then(singleUser => {
        const role = []
        singleUser.roles.forEach(r => {
            role.push(r.name.replace("ROLE_", ""))
        })
        let nav = "<a class='navbar-brand' href='/user'>" + singleUser.email + " with roles: " + role + "</a>"
        nav += "<a class= 'navbar-brand' href='/j_spring_security_logout'>Logout</a>"
        document.getElementById("navbar").innerHTML = nav
    })

 function fillModal (element,id) {
     userFetch.oneUSer(id)
        .then(resp => resp.json())
        .then(singleUser => {
            element.querySelector("input[name = id]").value = singleUser.id
            element.querySelector("input[name = name]").value = singleUser.name
            element.querySelector("input[name = lastName]").value = singleUser.lastName
            element.querySelector("input[name = age]").value = singleUser.age
            element.querySelector("input[name = email]").value = singleUser.email
            element.querySelector("input[name = password]").value = singleUser.password
            element.querySelector("select[name = roles]").value = singleUser.roles
        })
}

async function getUsersTable() {
    let table = ""

    await userFetch.allUsers()
        .then(response => response.json())
        .then(users => users.forEach(user => {

            const role = []
            user.roles.forEach(r => {
                role.push(r.name.replace("ROLE_", ""))
            })

            table += "<tr id =" + user.id +">"
            table += "<td> " + user.id + "</td>"
            table += "<td>" + user.name + "</td>"
            table += "<td>" + user.lastName + "</td>"
            table += "<td>" + user.age + "</td>"
            table += "<td>" + user.email + "</td>"
            table += "<td>" + role + "</td>"
            table += "<td><button  class='btn btn-info' data-toggle= 'modal' data-target = 'editmodal'" +
                " data-action ='edit' data-userid = " + user.id + "edit" + " >Edit</button></td>"
            table += "<td><button class= 'btn btn-danger' data-toggle= 'modal'" +
                " data-target = 'deleteModal' data-action ='delete' data-userid = " + user.id + "delete" + " >Delete</button></td></tr>"

        }))
    document.getElementById("userTable").innerHTML = table

    const defaultModal = document.getElementById("editModal")
    const deleteModal = document.getElementById("deleteModal")

    const buttons = document.querySelectorAll(".btn-info")
    buttons.forEach(el => {
        el.addEventListener("click", evt => {
            let targetButton = evt.currentTarget
            let buttonUserId = targetButton.getAttribute('data-userid');
            console.log(buttonUserId)
            defaultModal.setAttribute('data-userid', buttonUserId);
            defaultModal.style.display = "block"

            const span = defaultModal.getElementsByTagName("span")[0]
            span.onclick = () => {
                defaultModal.style.display = "none"
            }

            window.onclick = (ev) => {
                if (ev.target === defaultModal) {
                    defaultModal.style.display = "none"
                }
            }

            const closeButton = document.getElementById("closeEdit")
            closeButton.onclick = () => {
                defaultModal.style.display = "none"
            }

            const id = buttonUserId.replace("edit","")
            fillModal(defaultModal,id)

            let form = document.getElementById("editForm")
            form.onsubmit = async (ev) => {
                ev.preventDefault()
                defaultModal.style.display = "none"

                await fetch("http://localhost:8080/rest/admin/" + id, {
                    method: "PUT",
                    body: new FormData(form)
                })
                await getUsersTable()
            }
        })
    })

    const buttonsDelete = document.querySelectorAll(".btn-danger")
    buttonsDelete.forEach(el => {
        el.addEventListener("click", evt => {
            let targetButton = evt.currentTarget
            let buttonUserId = targetButton.getAttribute('data-userid');
            deleteModal.setAttribute('data-userid', buttonUserId);

            deleteModal.style.display = "block"

            const span = deleteModal.getElementsByTagName("span")[0]
            span.onclick = () => {
                deleteModal.style.display = "none"
            }

            window.onclick = (ev) => {
                if (ev.target === deleteModal) {
                    deleteModal.style.display = "none"
                }
            }
            const closeButton = document.getElementById("closeDelete")
            closeButton.onclick = () => {
                deleteModal.style.display = "none"
            }
            const id = deleteModal.getAttribute('data-userid').replace("delete","")

            fillModal(deleteModal,id)

            const form = document.getElementById("deleteForm")
            form.onsubmit = async (ev) => {
                ev.preventDefault()
                deleteModal.style.display = "none"

                 await fetch("http://localhost:8080/rest/admin/" + id, {
                    method: "DELETE",
                    body: new FormData(form)
                })
                await getUsersTable().then()
            }
        })
    })
}
getUsersTable()



