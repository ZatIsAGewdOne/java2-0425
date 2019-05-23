

$(function() {

    // cia viskas bus vyksoma po to kai bus uzkrautas puslapis

    var next = document.getElementById("next");
    var prev = document.getElementById("prev");

    var offset = 0;
    var pageSize = 10;

    // next.addEventListener('click', function(event) {
    //     offset += pageSize;
    //     displayEmployeeList(pageSize, offset);
    // });
    //
    //
    // displayEmployeeList(pageSize, offset);
});



// function displayEmployeeList(pageSize, offset) {
//
//     var tbody = document.getElementById("emp-table");
//     tbody.innerHTML = "<tr><td>Working....</td>";
//
//     $.get("api/employee", {pageSize: pageSize, offset: offset}).done(function(response) {
//
//         if (response) {
//             tbody.innerHTML = "";
//             response.forEach(function (x) {
//                 tbody.innerHTML += "<tr>" +
//                     "<td>" + x.empNo + "</td>" +
//                     "<td>" + x.firstName + "</td>" +
//                     "<td>" + x.lastName + "</td>" +
//                     "<td>" + x.gender + "</td>" +
//                     "<td>" + x.birthDate + "</td>" +
//                     "<td>" + x.hireDate + "</td>" +
//                     "</tr>"
//             })
//         }
//
//     }).fail(function() {
//         //TODO kazkoks klaidos parodymas
//     });
// }