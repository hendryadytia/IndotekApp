
<script>
    export let width = 200
    export let height = 80
    export let style
    export let url
    let urlf =  url
</script>
<div style="background-image:url({url}) ;background-size: cover;width: {width}px; height: {height}px; {style}">
    <!-- <img style="width: {height}px; height: {width}px;" src={url} alt="foto"/> -->
    
</div>

<style>
    div{
        background-color: rgba(0,0,0,0.16);
        border-radius: 5px;
    }
</style>