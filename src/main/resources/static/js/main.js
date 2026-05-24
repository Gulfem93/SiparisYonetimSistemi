document.addEventListener("DOMContentLoaded", () => {
    initUserMenu();
    initPasswordToggle();
    initFormValidation();
    initAddToCartButtons();
    initDeleteConfirm();
});

function initUserMenu() {
    const menuButton = document.getElementById("menuButton");
    const dropdownMenu = document.getElementById("dropdownMenu");

    if (!menuButton || !dropdownMenu) {
        return;
    }

    menuButton.addEventListener("click", () => {
        dropdownMenu.classList.toggle("show");
    });

    document.addEventListener("click", (event) => {
        if (!event.target.closest(".user-menu")) {
            dropdownMenu.classList.remove("show");
        }
    });
}

function initPasswordToggle() {
    const passwordInputs = document.querySelectorAll("input[type='password']");

    passwordInputs.forEach((input, index) => {
        const wrap = input.closest(".input-wrap");
        if (!wrap) {
            return;
        }

        const toggleButton = document.createElement("button");
        toggleButton.type = "button";
        toggleButton.textContent = "Goster";
        toggleButton.setAttribute("aria-label", "Sifreyi goster veya gizle");
        toggleButton.dataset.passwordToggle = String(index);
        toggleButton.style.position = "absolute";
        toggleButton.style.right = "12px";
        toggleButton.style.top = "50%";
        toggleButton.style.transform = "translateY(-50%)";
        toggleButton.style.border = "none";
        toggleButton.style.background = "transparent";
        toggleButton.style.color = "#1d65b0";
        toggleButton.style.fontWeight = "700";
        toggleButton.style.cursor = "pointer";

        input.style.paddingRight = "74px";

        toggleButton.addEventListener("click", () => {
            const isPassword = input.type === "password";
            input.type = isPassword ? "text" : "password";
            toggleButton.textContent = isPassword ? "Gizle" : "Goster";
        });

        wrap.appendChild(toggleButton);
    });
}

function initFormValidation() {
    setRequired("#username");
    setRequired("#password");
    setRequired("#name");
    setRequired("#email");
    setRequired("#accountType");

    const forms = document.querySelectorAll("form");
    forms.forEach((form) => {
        form.addEventListener("submit", (event) => {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
                showFeedback(form, "Lutfen zorunlu alanlari dogru sekilde doldurun.");
                highlightInvalidInputs(form);
            }
        });
    });
}

function setRequired(selector) {
    const field = document.querySelector(selector);
    if (field) {
        field.required = true;
    }
}

function highlightInvalidInputs(form) {
    const fields = form.querySelectorAll("input, select, textarea");
    fields.forEach((field) => {
        if (field.checkValidity()) {
            field.style.borderColor = "";
            field.style.boxShadow = "";
            return;
        }

        field.style.borderColor = "#dc2626";
        field.style.boxShadow = "0 0 0 3px rgba(220, 38, 38, 0.15)";
    });
}

function showFeedback(form, message) {
    let box = form.querySelector("[data-js-feedback='true']");
    if (!box) {
        box = document.createElement("p");
        box.dataset.jsFeedback = "true";
        box.style.margin = "2px 0 0";
        box.style.fontSize = "13px";
        box.style.color = "#dc2626";
        form.prepend(box);
    }
    box.textContent = message;
}

function initAddToCartButtons() {
    const addButtons = document.querySelectorAll(".add-btn");
    if (addButtons.length === 0) {
        return;
    }

    addButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const row = button.closest("tr");
            const quantityInput = row ? row.querySelector(".quantity-input") : null;
            const quantity = quantityInput ? Number(quantityInput.value) : 0;

            if (!quantityInput || Number.isNaN(quantity) || quantity < 1) {
                alert("Lutfen gecerli bir adet girin.");
                return;
            }

            const originalText = button.textContent;
            button.textContent = "Eklendi";
            button.disabled = true;

            setTimeout(() => {
                button.textContent = originalText;
                button.disabled = false;
            }, 1000);
        });
    });
}

function initDeleteConfirm() {
    const deleteButtons = document.querySelectorAll(".delete-btn");
    deleteButtons.forEach((button) => {
        button.addEventListener("click", (event) => {
            const isApproved = confirm("Bu urunu silmek istediginize emin misiniz?");
            if (!isApproved) {
                event.preventDefault();
            }
        });
    });
}
