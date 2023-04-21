let selectedRow;

$(document).ready(function() {
    $('.course-members-row').click(function() {
        const theRows  = $('.course-members-row');
        const theOptions  = $('.list-options');

        if (this.classList.contains('is-selected')) {
            console.log("Row has already been chosen, unselecting it now.");
            theRows.removeClass('is-selected');
            theOptions.css("visibility", "hidden");
            selectedRow = null
            return;
        }
        selectedRow = this;
        console.log($(this).find('.row-id-value').text() + " selected.");
        theRows.removeClass('is-selected');
        theOptions.css("visibility", "visible");
        selectedRow.classList.add('is-selected');
    });



    let timeout = null;

    $('#student-id').on('input', function() {
        // Clear any existing timeout
        clearTimeout(timeout);
        // Get the current value of the input element
        let value = $(this).val();
        // Set a new timeout to call the function after a delay
        timeout = setTimeout(function() {
            getStudentName(value)
        }, 500); // 500ms delay
    });

    function getStudentName(studentId) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", contextPath + "/manage-course?" + "listOperation=getName&studentId=" + studentId);
        xhr.setRequestHeader("Content-Type", "text/plain");
        xhr.onreadystatechange = function() {
            if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                // Handle successful response
                var response = this.responseText;
                document.getElementById("student-name").value = response;
            } else {
                document.getElementById("student-name").value = "";
            }
        };
        xhr.send();
    }
});

function removeStudent() {
    if (selectedRow != null) {
        fetch(contextPath + '/manage-course', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'id=' + courseId  + '&year='+ courseYear + '&listOperation=remove&' +
                'studentId=' + $(selectedRow).find('.row-id-value').text()
        }).then(r => {
            location.reload();
        });

    }
}

function updateGrades() {
    location.href = contextPath + '/edit-grades?courseId=' + courseId + '&year=' + courseYear +
        '&studentId=' + $(selectedRow).find('.row-id-value').text() +
        "&studentName=" + $(selectedRow).find('.row-name-value').text();
}







// function addStudent() {
//     if (selectedRow != null) {
//         fetch(contextPath + '/manage-course', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/x-www-form-urlencoded'
//             },
//             body: 'id=' + courseId + '&listOperation=add&' +
//                 'studentId=' + studentId
//         }).then(r => {
//             location.reload();
//         });
//     }
// }
