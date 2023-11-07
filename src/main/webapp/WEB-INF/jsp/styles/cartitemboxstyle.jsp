<style>
    .cartItemBox {
        /* Display */
        display: block;

        /* Size */
        width: 1350px;
        height: 100px;

        /* Border */
        border-width: 1px;
        border-style: solid;
        border-color: black;

        /* Padding */
        padding: 5px;

        /* Font/Text */
        font-family: Arial;
        text-align: left;
    }

    .cartItemBox > * {
        /* Vertical alignment */
        vertical-align: middle;
    }

    .cartItemSelectorBox {
        /* Display */
        display: inline-block;

        /* Size */
        width: fit-content;
        height: fit-content;

        /* Margin */
        margin-right: 15px;
    }

    .cartItemSelectorBox input[type="checkbox"] {
        /* Display */
        display: none;

        /* Vertical align */
        vertical-align: middle;
    }

    .cartItemSelectorBox input[type="checkbox"] + label::before {
        /* Content */
        content: "";

        /* Display */
        display: inline-block;

        /* Size */
        width: 25px;
        height: 25px;

        /* Background */
        background-color: white;

        /* Border */
        border-width: 1px;
        border-style: solid;
        border-color: black;

        /* Cursor */
        cursor: pointer;
    }

    .cartItemSelectorBox input[type="checkbox"]:checked + label::before {
        /* Background */
        background-color: dodgerblue;
    }

    .cartItemIcon {
        /* Display */
        display: inline-block;

        /* Size */
        width: 75px;
        height: 75px;

        /* Background */
        background-color: rgb(220, 220, 220);

        /* Border */
        border-width: 1px;
        border-style: solid;
        border-color: black;
        border-radius: 5px;
    }

    .cartItemNameBox {
        /* Display */
        display: inline-block;

        /* Size */
        width: 350px;
        height: fit-content;

        /* Font/Text */
        font-size: 25px;
        font-weight: bold;
        text-align: center;
    }

    .cartItemPriceBox {
        /* Display */
        display: inline-block;

        /* Size */
        width: 200;
        height: fit-content;

        /* Font/Text */
        font-size: 25px;
        text-align: center;
    }

    .cartItemAmountBox {
        /* Display */
        display: inline-block;

        /* Size */
        width: 300px;
        height: fit-content;

        /* Font/text */
        font-size: 25px;
        text-align: center;
    }

    .cartItemAmountTitle {
        /* Display */
        display: inline;

        /* Font/text */
        font-weight: bold;
    }

    .cartItemAmountSettingBtn {
        /* Display */
        display: inline-block;

        /* Font/text */
        font-weight: bold;

        /* Size */
        width: 35px;
        height: 35px;

        /* Background */
        background-color:rgb(220, 220, 220);

        /* Border */
        border-width: 1px;
        border-style: solid;
        border-color: black;
    }

    .cartItemAmount {
        /* Display */
        display: inline;
    }

    .cartItemTotalPriceBox {
        /* Display */
        display: inline-block;

        /* Size */
        width: 250px;
        height: fit-content;

        /* Font/text */
        font-size: 20px;
    }

    .removeCartItemBox {
        /* Display */
        display: inline-block;

        /* Size */
        width: 100px;
        height: fit-content;

        /* Font/text */
        font-size: 25px;
        font-weight: bold;
        text-align: right;
    }

    .removeCartItemUrl {
        /* Display */
        display: inline;
    }
</style>