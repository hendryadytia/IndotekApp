<script>
    export let title
    export let isi
    export let style
</script>
<div style="display: flex; {style}">
    <p style="width: 128px; font-family: 'Helvetica'; font-weight: bold;">{title}</p>
    <p style=" font-family: 'Helvetica'; width: 370px">{isi}</p>
</div>