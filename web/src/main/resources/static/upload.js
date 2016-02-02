        function uploadFile() {
            var form = document.getElementById('file-form');
            var fileSelect = document.getElementById('file');
            var uploadButton = document.getElementById('upload-button');

            form.onsubmit = function(event) {
                event.preventDefault();
                var files = fileSelect.files;
                var formData = new FormData();
                formData.append('file', files[0], files[0].name);

                var xhr = new XMLHttpRequest();
                xhr.open('POST', '/', true);

                xhr.onload = function () {
                  if (xhr.status === 200) {
                    // File(s) uploaded.
                    uploadButton.innerHTML = 'Upload';
                    poll(xhr.responseText);
                  } else {
                    alert('An error occurred!');
                  }
                };

                xhr.send(formData);
            }
        }
        function poll(id) {
            var pollId = setInterval(
            function() {
                var xhr = new XMLHttpRequest();
                xhr.open('GET', '/poll/' + id, true);
                xhr.onload = function () {
                    if (xhr.status === 200) {
                        var convertJob = JSON.parse(xhr.responseText);
                        if(convertJob.thumbnail){
                            $('#thumbnail').attr('src', convertJob.thumbnail);
                        }
                        if(convertJob.lowres){
                            $('#lowres').attr('src', convertJob.lowres);
                        }
                        if(convertJob.highres){
                            $('#highres').attr('src', convertJob.highres);
                        }

                        if(convertJob.thumbnail && convertJob.lowres && convertJob.highres) {
                            clearInterval(pollId);
                        }
                    } else {
                        alert('An error occurred!');
                    }
                }
            }, 5000);

            xhr.send();
        }