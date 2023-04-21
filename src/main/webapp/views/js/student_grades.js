let selectedRow;

$(document).ready(function() {
    $('.student-grades-row').click(function() {
        const theRows  = $('.student-grades-row');
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

    $('#year').change(function() {
        $('#yearSubmit').submit();
    });
});
