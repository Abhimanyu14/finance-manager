//  1. Snackbar
//
//  ScaffoldMessenger.of(context)
//    .showSnackBar(SnackBar(content: Text('Processing Data')));
//
//
//  2. Date picket
//
//  showDatePicker(
//    context: context,
//    initialDate: widget.date,
//    firstDate: DateTime(1900),
//    lastDate: DateTime(2100),
//  );
//
//
//  3. Bottom sheet
//
// showModalBottomSheet<void>(
//     context: context,
//     shape: RoundedRectangleBorder(
//       borderRadius: BorderRadius.circular(8.0),
//     ),
//     builder: (BuildContext context) {
//       return Container(
//         decoration: BoxDecoration(
//             borderRadius: BorderRadius.only(
//               topLeft: Radius.circular(8.0),
//               topRight: Radius.circular(8.0),
//             ),
//             color: Colors.white),
//         child: Column(
//           mainAxisAlignment: MainAxisAlignment.end,
//           children: [
//             Row(
//               mainAxisAlignment: MainAxisAlignment.end,
//               children: [
//                 TextButton(
//                   onPressed: () => Navigator.pop(context),
//                   child: Padding(
//                     padding: const EdgeInsets.all(8.0),
//                     child: const Text('Save'),
//                   ),
//                 ),
//               ],
//             ),
//           ],
//         ),
//       );
//     },
//   );
//
//
//
