fetch("http://localhost:8080/rest/admin/user")
    .then(response => response.json())
    .then(singleUser => {
        const role = []
        singleUser.roles.forEach(r => {
            role.push(r.name.replace("ROLE_", ""))
        })
        let nav = "<a class='navbar-brand' href='/user'>" + singleUser.email + " with roles: " + role + "</a>"
        nav+= "<a class= 'navbar-brand' href='/j_spring_security_logout'>Logout</a>"
        document.getElementById("navbar").innerHTML = nav
    })

const form = document.getElementById("newUser")
form.onsubmit = async (ev) => {
    ev.preventDefault()

    await fetch("http://localhost:8080/rest/admin/",{
        method: "POST",
        body: new FormData(form),
    })
    document.getElementById("newUser").reset()
    alert("Пользователь успешно добавлен")
}
