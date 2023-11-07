<style>
    .radioselector {
        /* Display */
        display: none;
    }

    .radioselector + label::before {
        /* Content */
        content: "";

        /* Display */
        display: inline-block;

        /* Size */
        width: 15px;
        height: 15px;

        /* Border */
        border: 5px solid black;
        border-radius: 5px;

        /* Background */
        background-color: rgba(0,0,0,0);

        /* Align */
        vertical-align: middle;

        /* Cursor */
        cursor: pointer;
    }

    .radioselector:checked + label::before {
        /* Background */
        background-color: dodgerblue;
    }

    input[value="Female"].radioselector:checked + label::before {
        /* Background */
        background-color: fuchsia;
    }
</style>