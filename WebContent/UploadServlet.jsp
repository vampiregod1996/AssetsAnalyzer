<%-- 
    Document   : upload
    Created on : Nov 3, 2012, 12:31:16 PM
    Author     : Amila
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Force latest IE rendering engine or ChromeFrame if installed -->
        <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
        <meta charset="utf-8">
        <title>jQuery File Upload Demo</title>
        <meta name="description" content="File Upload widget with multiple file selection, drag&amp;drop support, progress bar and preview images for jQuery. Supports cross-domain, chunked and resumable file uploads. Works with any server-side platform (Google App Engine, PHP, Python, Ruby on Rails, Java, etc.) that supports standard HTML form file uploads.">
        <meta name="viewport" content="width=device-width">
        <!-- Bootstrap CSS Toolkit styles -->
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
        <!-- Generic page styles -->
        <link rel="stylesheet" href="css/style.css">
        <!-- Bootstrap styles for responsive website layout, supporting different screen sizes -->
        <link rel="stylesheet" href="css/bootstrap-responsive.min.css">
        <!-- Bootstrap CSS fixes for IE6 -->
        <!--[if lt IE 7]><link rel="stylesheet" href="http://blueimp.github.com/cdn/css/bootstrap-ie6.min.css"><![endif]-->
        <!-- Bootstrap Image Gallery styles -->
        <link rel="stylesheet" href="css/bootstrap-image-gallery.min.css">
        <!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
        <link rel="stylesheet" href="css/jquery.fileupload-ui.css">
        <!-- Shim to make HTML5 elements usable in older Internet Explorer versions -->
        <!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
    </head>
    <body>
            <!-- The file upload form used as target for the file upload widget -->
			<form id="fileupload" action="upload" method="POST" enctype="multipart/form-data" >
			<!-- The table listing the files available for upload/download -->
			    <table  id="tableborder" role="presentation" class="table table-striped" >
			     <tbody id="tbodyborder" class="files" data-toggle="modal-gallery" data-target="#modal-gallery">
			     </tbody>
			    </table>
			</form>
            <br>
            
        </div>

        <!-- The template to display files available for upload -->
        <script id="template-upload" type="text/x-tmpl">
            {% for (var i=0, file; file=o.files[i]; i++) { %}
        <tr id="trborder" class="template-upload fade">
            <td ><span>包名 : </span></td>
            <td class="name"><a class="uploadname cutTitle "  href="javascript:0" title="{%=file.name%}" style="text-decoration: none;">{%=file.name%}</a></td>
            <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
            {% if (file.error) { %}
            <td class="error" colspan="2" style="color: #DF1010;">{%=file.error%}</td>
            {% } else if (o.files.valid && !i) { %}
            <td>
                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="bar" style="width:0%;"></div></div>
            </td>
            <td class="start">{% if (!o.options.autoUpload) { %}
                <button class="btn btn-primary">
                    <i class="icon-upload icon-white"></i>
                    <span>Start</span>
                </button>
                {% } %}</td>
            {% } else { %}
            <td class="error" colspan="2"></td>
            {% } %}
            <td class="cancel">{% if (!i) { %}
                <button class="btn btn-warning">
                    <i class="icon-ban-circle icon-white"></i>
                    <span>Cancel</span>
                </button>
                {% } %}</td>
        </tr>
        {% } %}
    </script>
    <!-- The template to display files available for download -->
    <script id="template-download" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
        <tr class="template-download fade">
            {% if (file.error) { %}
            <td></td>
            <td class="name"><span>{%=file.name%}</span></td>
            <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
            <td class="error" colspan="1"><span class="label label-important">Error</span> {%=file.error%}</td>
            {% } else { %}
            <td ><span>包名 : </span></td>
            <td class="name">
                <span class="bundleTitle cutTitle" onclick="getData('{%=file.bundleID%}');getSenData('{%=file.bundleID%}');getABData('{%=file.bundleID%}');return false;">{%=file.name%}</span>
            </td>
            <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
            <td class="error" colspan="1"></td>
            {% } %}
            <td class="delete" id="deletespan">
                <button class="btn btn-danger" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}"{% if (file.delete_with_credentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %} style="display: none;">
                        <i class="icon-trash icon-white"></i>
                    <span>Delete</span>
                </button>
            </td>
			<td bundleID="{%=file.bundleID%}" class="analyzeBtn"><button id="analyze" onclick="getData('{%=file.bundleID%}');return false;" style="display: none;"><span>Analyze</span></button></td>
        </tr>
        {% } %}
    </script>

    <script src="js/jquery-1.8.2.min.js"></script>
    <script src="js/vendor/jquery.ui.widget.js"></script>
    <script src="js/tmpl.min.js"></script>
    <script src="js/load-image.min.js"></script>
    <script src="js/canvas-to-blob.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-image-gallery.min.js"></script>
    <script src="js/jquery.iframe-transport.js"></script>
    <script src="js/jquery.fileupload.js"></script>
    <script src="js/jquery.fileupload-fp.js"></script>
    <script src="js/jquery.fileupload-ui.js"></script>
    <script src="js/locale.js"></script>
    <script src="js/main.js"></script>
</body> 
</html>