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