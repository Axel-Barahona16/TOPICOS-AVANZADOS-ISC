using System;
using System.IO;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace Ejercicio6
{
    public partial class MainPage : ContentPage
    {
        int contador;
        String _filename = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.LocalApplicationData), "notas.txt");
        public MainPage()
        {
            InitializeComponent();
            contador = 0;
            if (File.Exists(_filename))
            {
                entrada.Text = File.ReadAllText(_filename);
            }
        }

        private void Button_Clicked(object sender, EventArgs e)
        {
            File.WriteAllText(_filename, entrada.Text);
        }

        private void Button_Clicked_1(object sender, EventArgs e)
        {
            if (File.Exists(_filename))
            {
                File.Delete(_filename);
            }
            entrada.Text = string.Empty;
        }
    }
}
