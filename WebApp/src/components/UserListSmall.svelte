<script>
    export let nama="nama"
    export let alamat="alamat"
    export let tanggal="Kamis, 27 Agustus"
    export let urutan=1
    let background = "#EFEAEA"
    if (urutan%2 != 0) {
    background ="white"    
    }
</script>

<div style="display: flex;padding: 5px 0px; gap:5px;background-color: white;margin: 0px -10px;padding-left: 10px;padding-right: 10px;background: {background};">
    <b style="font-weight:bold; ">{nama}</b> <p>{"("  + alamat + ")"}</p> <p>{tanggal}</p>
</div>