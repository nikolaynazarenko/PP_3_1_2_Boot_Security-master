function getUserTable () {

     fetch("http://localhost:8080/rest/admin/user")
        .then(response => response.json())
        .then(singleUser => {
            const role = []
                singleUser.roles.forEach(r => {
                role.push(r.name.replace("ROLE_", ""))
            })

            let table = ""
            table += "<tr>"
            table += "<td> " + singleUser.id + "</td>"
            table += "<td>" + singleUser.name + "</td>"
            table += "<td>" + singleUser.lastName + "</td>"
            table += "<td>" + singleUser.age + "</td>"
            table += "<td>" + singleUser.email + "</td>"
            table += "<td>" + role + "</td></tr>"
            document.getElementById("singleUser").innerHTML = table


            let nav = "<a class='navbar-brand' href='/user'>" + singleUser.email + " with roles: " + role + "</a>"
            nav+= "<a class= 'navbar-brand' href='/j_spring_security_logout'>Logout</a>"
            document.getElementById("navbar").innerHTML = nav
        })
}
getUserTable()


