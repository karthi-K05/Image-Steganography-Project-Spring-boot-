 function updateFileName() {
            var input = document.getElementById('fileID');
            var fileName = input.files.length > 0 ? input.files[0].name : "File isn't chosen";
            document.getElementById('fileName').innerText = fileName;

            var reader = new FileReader();
            reader.onload = function() {
                var output = document.getElementById('output');
                output.src = reader.result;
            };
            reader.readAsDataURL(input.files[0]);
        }

        document.getElementById('decryptForm').addEventListener('submit', function(event) {
            event.preventDefault();
            var formData = new FormData(this);

            fetch(this.action, {
                method: 'POST',
                body: formData,
            })
            .then(response => response.text())
            .then(data => {
                document.getElementById('message').value = data;
            })
            .catch(error => console.error('Error:', error));
        });

function previewImage() {
    const fileInput = document.getElementById('fileID');
    const fileName = fileInput.files[0] ? fileInput.files[0].name : 'File isn\'t chosen';
    document.getElementById('fileName').innerText = fileName;

    const file = fileInput.files[0];
    const reader = new FileReader();
    reader.onload = function (e) {
        document.getElementById('output').src = e.target.result;
    };
    reader.readAsDataURL(file);
}

function uploadImage() {
    const formData = new FormData(document.getElementById('myForm'));

    fetch('/encrypt', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.base64Image) {
            showPopup(data.base64Image);
        } else {
            alert(data.error || 'An error occurred');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred');
    });
}

function showPopup(imageSrc) {
    document.getElementById('popup-image').src = 'data:image/png;base64,' + imageSrc;
    document.getElementById('download-link').href = 'data:image/png;base64,' + imageSrc;
    document.getElementById('popup-overlay').style.display = 'block';
    document.getElementById('popup').style.display = 'block';
}

function closePopup() {
    document.getElementById('popup-overlay').style.display = 'none';
    document.getElementById('popup').style.display = 'none';
}
