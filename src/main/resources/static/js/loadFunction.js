function loadHtml(id, fileName){
    alert("inside"+fileName);
    let xhttp;
    let element = document.getElementById(id);
    let file = fileName;
    if(file){
        xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function(){
            if(this.readyState == 4){
                if(this.status == 200){element.innerHTML = this.responseText;}
                if(this.status == 404){element.innerHTML = "<h2>Page Not Found</h2>";}
            }
        }
        xhttp.open("GET",'/index', true);
        xhttp.send();
        return;
    }
}

function loadapi(id, fileName){
    let xhttp;
    let element = document.getElementById(id);
    let file = fileName;
    if(file){
        xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function(){
            if(this.readyState == 4){
                if(this.status == 200){element.innerHTML = this.responseText;}
                if(this.status == 404){element.innerHTML = "<h2>Page Not Found</h2>";}
            }
        }
        xhttp.open("GET",'/api/index', true);
        xhttp.send();
        return;
    }
}

function loadbeftn(id, fileName){
    let xhttp;
    let element = document.getElementById(id);
    let file = fileName;
    if(file){
        xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function(){
            if(this.readyState == 4){
                if(this.status == 200){element.innerHTML = this.responseText;}
                if(this.status == 404){element.innerHTML = "<h2>Page Not Found</h2>";}
            }
        }
        xhttp.open("GET",'/beftn/index', true);
        xhttp.send();
        return;
    }
}