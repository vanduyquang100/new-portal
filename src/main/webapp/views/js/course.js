let selectedRow;

$(document).ready(function() {
    $('.course-row').click(function() {
        const theRows  = $('.course-row');
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
});

function editCourse() {
    if (selectedRow != null) {
        fetch(contextPath + '/edit-course?id=' + $(selectedRow).find('.row-id-value').text() + '&year=' + $(selectedRow).find('.row-year-value').text(), {
            method: 'GET'
        }).then(r => {
            location.href = r.url;
        });
    }
}
function addCourse() {
    fetch(contextPath + '/add-course', {
        method: 'GET'
    }).then(r => {
        location.href = r.url;
    });
}

function deleteCourse() {
    if (selectedRow != null) {
        fetch(contextPath + '/courses', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'listOperation=delete&' +
                'courseId=' + $(selectedRow).find('.row-id-value').text()
        }).then(r => {
            location.reload();
        });

    }
}

function manageCourse() {
    if (selectedRow != null) {
        fetch(contextPath + '/manage-course?id=' + $(selectedRow).find('.row-id-value').text()
            + '&year=' + $(selectedRow).find('.row-year-value').text(), {
            method: 'GET'
        }).then(r => {
            location.href = r.url;
        });
    }
}


function switchSortType() {
    const sortInput = $('#sortTypeValue');
    if (sortInput.val() === '0') {
        sortInput.val('1');
    } else {
        sortInput.val('0');
    }

}