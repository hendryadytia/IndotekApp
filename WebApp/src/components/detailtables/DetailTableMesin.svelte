<script>
     import {apiurl,apiurlonly} from '../../helper/apiurl.js'
    import ButtonSmall from '../ButtonSmall.svelte'
    import Card from '../Card.svelte'
    import DataList from '../DataListForCard.svelte'
    import IMP from '../ImagePlaceHolder.svelte'
    import X from 'jquery'
    import { getNamaHari } from '../../helper/iddate';

    export let id
    let data
    let jenis,merek,keterangan,pemilik,idpel,alamat,notelp,teknisi,tglmulai,diagnosa,selesai,harga,statuspengerjaan,fotobefore,fotoafter = "-"
    var currformatter = new Intl.NumberFormat('id-ID', {
                                            style: 'currency',
                                            currency: 'IDR',})
    // function getData(id){
    //     //kirim perintah ke server pakai ajax, ambil data yg ber id sekian, ini sementara dimock pake fetch 
    //     fetch('dataDummies.txt')
    //     .then(response => response.json())
    //     .then(json => loadData(json)); 
    // }
    // getData(id)
    X.ajax({
            url:apiurl+"detailmesin",
            type:"GET",
            data: {
                id: id
            },
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result){
                jenis = result.data.jenis_mesin
                merek = result.data.merek
                // nomorseri = result.data.mesin.merek
                keterangan = result.data.keterangan
                pemilik = result.data.nama_pelanggan
                idpel = result.data.id_pelanggan
                alamat = result.data.alamat
                notelp = result.data.no_telp_pelanggan
                teknisi = result.data.nama_teknisi || "-"

                switch(result.data.status){
                    case "0":
                        tglmulai =  "-"
                        diagnosa = "-"
                        selesai = "-"
                        harga = "-"
                        break;
                    case "1": 
                        tglmulai = result.datadiag.tgl_mulai || "-"
                        diagnosa = "-"
                        selesai = "-"
                        harga = "-"
                        break;
                    default:
                        tglmulai = result.datadiag.tgl_mulai + "("+getNamaHari(result.datadiag.tgl_mulai )+")" || "-"
                        diagnosa = result.datadiag.diagnosa || "-"
                        selesai = result.datadiag.est_selesai + " hari" || "-"
                        harga = currformatter.format(result.datadiag.biaya)|| "-"
                        break;

                }

                
                statuspengerjaan = result.data.nama_status
                fotobefore = apiurlonly +result.data.foto_before
                fotoafter = apiurlonly +result.data.foto_after
                // nama = result.data.nama
                // username = result.data.username
                // bergabung = result.data.tgl_dibuat
                // idt = result.data.id
                // notelp = result.data.no_telp
                // keahlian = result.data.keahlian
                // alamat = result.data.alamat

                console.log(result.data.pelanggan);
                
            }         
        },
    )

    function getCookie(cname){
        let name = cname + "=";
        let ca = document.cookie.split(';');
        for(let i = 0;i< ca.length; i++){
            let c = ca[i]
            while (c.charAt(0) == ' '){
                c = c.substring(1)
            }
            if(c.indexOf(name) == 0){
                return c.substring(name.length,c.length)
            }
        }
        return ""
    }

    // function loadData(jsondata){
    //     console.log(jsondata.data[1]);
    // }
    let lihatDataPemilik = false
    
    
    
    
</script>
<div style="display: flex; flex-direction:column; gap:8px">

    <Card width=500 useBorder> 
        <div style="display: flex; flex-direction: column; gap:16px; position:relative">    
            
            <ButtonSmall on:click={()=> lihatDataPemilik?lihatDataPemilik=false:lihatDataPemilik=true} style="position: absolute; right:25px; top:0; font-size: 14px; margin-top: -4px">
                {#if lihatDataPemilik}
                Tutup
                {:else}
                Lihat
                {/if}
            </ButtonSmall>
             <DataList title="Pemilik" isi={pemilik}/>
                {#if lihatDataPemilik}
                
                        <DataList title="ID Pelanggan" isi={idpel}/>
                        <DataList title="Alamat" isi={alamat}/>
                        <DataList title="Nomor Telp" isi={notelp}/>
                
                {/if}
        </div>
   
    </Card>
    
    <Card width=500 useBorder> 
        <div style="display: flex; flex-direction: column; gap:16px; ">
            <DataList title="Jenis Mesin" isi={jenis}/>
            <DataList title="Merek" isi={merek}/>
            
        </div>
    </Card>

    <Card width=500 useBorder>
        <div style="display: flex; gap:20px">
            <p style="font-weight:bold"> Foto saat sebelum</p>
            <IMP url={fotobefore} height=150 width=160/>
            <p style="font-weight:bold"> Foto saat selesai</p>
            <IMP url={fotoafter} height=150 width=160/>
        </div>
    </Card>

    <Card width=500 useBorder> 
        <DataList title="Keterangan" isi={keterangan}/>
    </Card>


    <Card width=500 useBorder> 
        <div style="display: flex; flex-direction: column; gap:16px;position: relative">
           
            <DataList title="Teknisi" isi={teknisi} />
            <DataList title="Tgl. Mulai" isi={tglmulai}/>
            <DataList title="Diagnosa" isi={diagnosa}/>
            <DataList title="est. Selesai" isi={selesai}/>
            <DataList title="est. Harga" isi={harga}/>
            <DataList title="Status" isi={statuspengerjaan}/>
        </div>
    </Card>
    
   
</div>