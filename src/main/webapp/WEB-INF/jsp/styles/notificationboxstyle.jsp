<style>
    .notificationBox {
        /* Display */
        display: block;

        /* Size */
        width: 750px;
        height: 100px;

        /* Padding */
        padding: 5px;
        padding-left: 10px;
        padding-right: 10px;

        /* Font */
        font-family: Arial;
        font-weight: normal;

        /* Background */
        background-color: white;
        
        /* Border */
        border-width: 1px;
        border-style: solid;
        border-color: rgb(130, 130, 130);
        border-radius: 10px;
    }

    .notificationBox[seen="false"] {
        /* Font */
        font-weight: bold;
    }

    .notificationTitle {
        /* Font */
        font-size: 25px;

        /* Text */
        text-align: left;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;

        /* Color */
        color: dodgerblue;

        /* Size */
        height: 50%;
    }

    .notificationDate {
        /* Font */
        font-size: 10px;

        /* Text */
        text-align: left;

        /* Color */
        border-color: rgb(130, 130, 130);

        /* Size */
        height: 50%;
    }
</style>