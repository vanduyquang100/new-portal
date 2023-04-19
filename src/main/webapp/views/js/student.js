let selectedRow;

$(document).ready(function() {
    $('.student-row').click(function() {
        const theRows  = $('.student-row');
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

function editStudent() {
    if (selectedRow != null) {
        fetch(contextPath + '/edit-student?id=' + $(selectedRow).find('.row-id-value').text(), {
            method: 'GET'
        }).then(r => {
            location.href = r.url;
        });
    }
}
function addStudent() {
    fetch(contextPath + '/add-student', {
        method: 'GET'
    }).then(r => {
        location.href = r.url;
    });
}

function deleteStudent() {
    if (selectedRow != null) {
        fetch(contextPath + '/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'listOperation=delete&' +
                'studentId=' + $(selectedRow).find('.row-id-value').text()
        }).then(r => {
            location.reload();
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