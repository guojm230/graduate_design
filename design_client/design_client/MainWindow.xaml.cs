using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace design_client
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void NextControl(object sender, RoutedEventArgs e)
        {
            var count = this.ControlContent.Items.Count;
            if (this.ControlContent.SelectedIndex < count - 1)
            {
                this.ControlContent.SelectedIndex++;
            }
        }

        private void PrevControl(object sender, RoutedEventArgs e)
        {
            var count = this.ControlContent.Items.Count;
            if (this.ControlContent.SelectedIndex > 0)
            {
                this.ControlContent.SelectedIndex--;
            }
        }

        private void ControlTab_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            var sel = this.ControlTab.SelectedIndex;
            if (this.ControlContent != null && sel>=0 && sel < this.ControlContent.Items.Count)
            {
                this.ControlContent.SelectedIndex = sel;
            }
        }
    }
}
